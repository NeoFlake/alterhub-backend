package com.alterhub.alterhubbackend.service.impl;

import com.alterhub.alterhubbackend.dto.AlteredCardDTO;
import com.alterhub.alterhubbackend.dto.CardListWrapper;
import com.alterhub.alterhubbackend.entity.*;
import com.alterhub.alterhubbackend.entity.Set;
import com.alterhub.alterhubbackend.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Mono;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.concurrent.ThreadLocalRandom;

import java.io.IOException;

import jakarta.annotation.PostConstruct;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlteredSyncService {

    private final CardRepository cardRepository;
    private final TypeRepository typeRepository;
    private final SubTypeRepository subTypeRepository;
    private final SetRepository setRepository;
    private final RarityRepository rarityRepository;
    private final FactionRepository factionRepository;

    private WebClient webClient;

    private final int ITEMS_PER_PAGE = 36;
    private final String IMAGE_FOLDER = "src/main/resources/static/images/cards/";

    private final List<String> FACTIONS = List.of("AX", "BR", "LY", "MU", "OR", "YZ");

    @PostConstruct
    @SuppressWarnings("unused")
    public void init() {
        String BASE_URL = "https://api.altered.gg/cards";
        webClient = WebClient.builder().baseUrl(BASE_URL).defaultHeader("User-Agent", "AlterHubSync/1.0").build();

        // Crée le dossier images si nécessaire
        File file = new File(IMAGE_FOLDER);
        if (!file.exists()) {
            boolean created = file.mkdirs();
            if (!created) {
                log.error("Impossible de créer le dossier {}", IMAGE_FOLDER);
            }
        }
    }

    public void syncAllCardsByFaction(SseEmitter emitter) {
        send(emitter, "Initialisation de la phase de synchronisation avec l'api Altered");
        for (String faction : FACTIONS) {
            send(emitter, "Démarrage de la synchronisation pour la faction : " + faction);
            int page = 1;
            boolean hasMore = true;

            while (hasMore) {
                List<AlteredCardDTO> cardDTOs = fetchCardsPage(faction, page);

                if (cardDTOs.isEmpty()) {
                    break;
                }

                int i = 0;
                for (AlteredCardDTO dto : cardDTOs) {
                    syncCard(dto);
                    i++;
                    if (i % 10 == 0) send(emitter, "Faction " + faction + " - " + i + " cartes synchronisées...");
                }

                send(emitter, "Faction " + faction + " : page " + page + " synchronisée");

                if (cardDTOs.size() < ITEMS_PER_PAGE) {
                    hasMore = false;
                } else {
                    page++;
                }
            }
            send(emitter, "Faction " + faction + " terminée");
        }
        send(emitter, "Synchronisation complète");
    }

    private List<AlteredCardDTO> fetchCardsPage(String faction, int page) {
        try {
            Mono<CardListWrapper> response = webClient.get().uri(uriBuilder -> uriBuilder.queryParam("page", page).queryParam("factions[]", faction).queryParam("itemsPerPage", ITEMS_PER_PAGE).queryParam("locale", "fr-fr").build()).retrieve().bodyToMono(CardListWrapper.class);

            CardListWrapper wrapper = response.block();
            if (wrapper == null) return Collections.emptyList();
            return wrapper.getHydraMember();
        } catch (Exception e) {
            log.error("Erreur fetching page {} pour faction {}", page, faction, e);
            return Collections.emptyList();
        }
    }

    // --- fetch détaillé avec retry/backoff/jitter ---
    private AlteredCardDTO fetchDetailedCard(String alteredId) {
        int attempt = 0;
        // nombre max de tentatives pour le fetch détaillé
        final int DETAIL_RETRY_MAX = 4;
        while (attempt < DETAIL_RETRY_MAX) {
            // backoff de base (sera multiplié exponentiellement)
            final long BASE_BACKOFF_MS = 200L;
            try {
                Mono<AlteredCardDTO> response = webClient.get()
                        .uri("/{id}?locale=fr-fr", alteredId)
                        .retrieve()
                        .bodyToMono(AlteredCardDTO.class);

                AlteredCardDTO detail = response.block();
                return detail == null ? new AlteredCardDTO() : detail;
            } catch (WebClientResponseException we) {
                HttpStatusCode status = we.getStatusCode();
                int statusCode = status.value();
                // si 429 or 503 on retryable server error -> backoff et retry
                if (statusCode == 429 || (statusCode >= 500 && statusCode < 600)) {
                    attempt++;
                    long backoff = BASE_BACKOFF_MS * (1L << (attempt - 1)); // exponentiel
                    // add jitter
                    long jitter = ThreadLocalRandom.current().nextLong(0, backoff / 2 + 1);
                    long sleepMs = backoff + jitter;
                    log.warn("Fetch détail {} -> HTTP {} (attempt {}/{}). Backoff {}ms", alteredId, status, attempt, DETAIL_RETRY_MAX, sleepMs);
                    try {
                        Thread.sleep(sleepMs);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                } else {
                    log.error("Erreur HTTP non retryable lors du fetch détail {} : status {}", alteredId, status);
                    return new AlteredCardDTO();
                }
            } catch (Exception e) {
                attempt++;
                long backoff = BASE_BACKOFF_MS * (1L << (attempt - 1));
                long jitter = ThreadLocalRandom.current().nextLong(0, backoff / 2 + 1);
                long sleepMs = backoff + jitter;
                log.warn("Exception fetch detail {} (attempt {}/{}): {} - backoff {}ms", alteredId, attempt, DETAIL_RETRY_MAX, e.getMessage(), sleepMs);
                try {
                    Thread.sleep(sleepMs);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        log.error("Échec du fetch détail après {} tentatives pour la carte {}", DETAIL_RETRY_MAX, alteredId);
        return new AlteredCardDTO(); // fallback vide
    }

    private void syncCard(AlteredCardDTO dto) {
        try {
            // On vérifie si la carte existe déjà
            Optional<Card> existingOpt = cardRepository.findByAlteredId(dto.getId());
            Card card = existingOpt.orElseGet(Card::new);

            // On mappe les champs simples
            card.setAlteredId(dto.getId());
            card.setReference(dto.getReference());
            card.setName(dto.getName());
            card.setIsBanned(dto.getIsBanned());
            card.setIsSuspended(dto.getIsSuspended());
            card.setIsErrated(dto.getIsErrated());

            // Type
            Type type = typeRepository.findByTypeId(dto.getCardType().getId()).orElseGet(() -> {
                Type newType = new Type();
                newType.setTypeId(dto.getCardType().getId());
                newType.setName(dto.getCardType().getName());
                newType.setReference(dto.getCardType().getReference());
                return typeRepository.save(newType);
            });
            card.setType(type);

            // Rarity
            Rarity rarity = rarityRepository.findByRarityId(dto.getRarity().getId()).orElseGet(() -> {
                Rarity newRarity = new Rarity();
                newRarity.setRarityId(dto.getRarity().getId());
                newRarity.setName(dto.getRarity().getName());
                newRarity.setReference(dto.getRarity().getReference());
                return rarityRepository.save(newRarity);
            });
            card.setRarity(rarity);

            // Faction
            Faction faction = factionRepository.findByFactionId(dto.getMainFaction().getId()).orElseGet(() -> {
                Faction newFaction = new Faction();
                newFaction.setFactionId(dto.getMainFaction().getId());
                newFaction.setName(dto.getMainFaction().getName());
                newFaction.setReference(dto.getMainFaction().getReference());
                newFaction.setColor(dto.getMainFaction().getColor());
                return factionRepository.save(newFaction);
            });
            card.setFaction(faction);

            // Sets
            List<Set> sets = new ArrayList<>();
            if (dto.getCardSet() != null) {
                Set set = setRepository.findBySetId(dto.getCardSet().getId()).orElseGet(() -> {
                    Set newSet = new Set();
                    newSet.setSetId(dto.getCardSet().getId());
                    newSet.setName(dto.getCardSet().getName());
                    newSet.setReference(dto.getCardSet().getReference());
                    return setRepository.save(newSet);
                });
                sets.add(set);
            }
            card.setSets(sets);

            // Sous types (appel détaillé si liste vide)
            if (dto.getCardSubTypes() == null || dto.getCardSubTypes().isEmpty() ||
                    dto.getElements() == null || dto.getElements().isEmpty()) {

                randomSleep();
                AlteredCardDTO detailed = fetchDetailedCard(dto.getReference());
                if (detailed.getCardSubTypes() != null && !detailed.getCardSubTypes().isEmpty()) {
                    dto.setCardSubTypes(detailed.getCardSubTypes());
                }
                if (detailed.getElements() != null && !detailed.getElements().isEmpty()) {
                    dto.setElements(detailed.getElements());
                }
            }

            // On map les sous types sur la carte à pousser en base de donnée
            if (dto.getCardSubTypes() != null && !dto.getCardSubTypes().isEmpty()) {
                List<SubType> subTypes = new ArrayList<>();
                for (var subTypesDTO : dto.getCardSubTypes()) {
                    SubType subType = subTypeRepository.findBySubTypeId(subTypesDTO.getId())
                            .orElseGet(() -> {
                                SubType newSubType = new SubType();
                                newSubType.setSubTypeId(subTypesDTO.getId());
                                newSubType.setName(subTypesDTO.getName());
                                newSubType.setReference(subTypesDTO.getReference());
                                return subTypeRepository.save(newSubType);
                            });
                    subTypes.add(subType);
                }
                card.setSubtypes(subTypes);
            }

            // On map l'élément sur la carte à pousser en base de donnée
            if (dto.getElements() != null) {
                Element element = card.getElement() != null ? card.getElement() : new Element();
                element.setMainCost(dto.getElements().getOrDefault("MAIN_COST", "0"));
                element.setRecallCost(dto.getElements().getOrDefault("RECALL_COST", "0"));
                element.setMountainPower(dto.getElements().getOrDefault("MOUNTAIN_POWER", "0"));
                element.setOceanPower(dto.getElements().getOrDefault("OCEAN_POWER", "0"));
                element.setForestPower(dto.getElements().getOrDefault("FOREST_POWER", "0"));
                element.setMainEffect(dto.getElements().getOrDefault("MAIN_EFFECT", ""));
                element.setEchoEffect(dto.getElements().getOrDefault("ECHO_EFFECT", ""));
                card.setElement(element);
            }

            if (dto.getImagePath() != null && !dto.getImagePath().isEmpty()) {
                String imgUrl = dto.getImagePath();
                String localPath = IMAGE_FOLDER + dto.getId() + ".jpg";
                downloadImage(imgUrl, localPath);
                card.setImage(localPath);
            }

            cardRepository.save(card);
            log.info("Carte synchronisée : {}", card.getName());

        } catch (Exception e) {
            log.error("Erreur sync card {}", dto.getName(), e);
        }
    }

    private void downloadImage(String url, String localPath) {
        try {
            File file = new File(localPath);
            if (!file.exists()) {
                FileCopyUtils.copy(new URL(url).openStream(), new FileOutputStream(file));
            }
        } catch (Exception e) {
            log.error("Erreur téléchargement image {}", url, e);
        }
    }

    private void send(SseEmitter emitter, String msg) {
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().data(msg));
            } catch (IOException ignored) {
            }
        }
    }

    // --- utilitaire : sleep aléatoire entre appels pour désynchroniser les requêtes ----
    private void randomSleep() {
        try {
            // borne haute du délai aléatoire
            final long MAX_DELAY_MS = 150L;
            // --- paramètres de throttling/retry (ajoute en tant que champs privés de la classe) ---
            // borne basse du délai aléatoire
            final long MIN_DELAY_MS = 50L;
            long delay = MIN_DELAY_MS + ThreadLocalRandom.current().nextLong(MAX_DELAY_MS - MIN_DELAY_MS + 1);
            Thread.sleep(delay);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            log.warn("Thread interrompu pendant randomSleep");
        }
    }

}