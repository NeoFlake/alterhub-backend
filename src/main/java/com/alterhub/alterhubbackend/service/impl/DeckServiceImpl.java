package com.alterhub.alterhubbackend.service.impl;

import com.alterhub.alterhubbackend.dto.DeckDTO;
import com.alterhub.alterhubbackend.entity.Deck;
import com.alterhub.alterhubbackend.entity.Player;
import com.alterhub.alterhubbackend.exception.*;
import com.alterhub.alterhubbackend.mapping.MappingService;
import com.alterhub.alterhubbackend.repository.DeckRepository;
import com.alterhub.alterhubbackend.repository.PlayerRepository;
import com.alterhub.alterhubbackend.service.DeckCountService;
import com.alterhub.alterhubbackend.service.interfaces.*;
import com.alterhub.alterhubbackend.validation.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.owasp.encoder.Encode;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeckServiceImpl implements DeckService {

    private final DeckRepository deckRepository;

    private final ValidationService validationService;
    private final MappingService mappingService;
    private final DeckCountService deckCountService;

    // Permet d'effectuer les appels db avec les dates permettant de cibler correctement les périodes temporelles recherchées
    // Dans ce cas pour taper le paramètre dateOfCreation qui est en LocalDate
    private final LocalDate startOfWeekLocalDate = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    private final LocalDate endOfWeekLocalDate = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
    private final LocalDate startOfMonthLocalDate = LocalDate.now().withDayOfMonth(1);
    private final LocalDate endOfMonthLocalDate = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());
    // Dans celui-ci, on tape sur le paramètre lastModification qui est en LocalDateTime
    private final LocalDateTime startOfDayLocalDateTime = LocalDate.now().atStartOfDay();
    private final LocalDateTime endOfDayLocalDateTime = LocalDateTime.now();
    private final LocalDateTime startOfWeekLocalDateTime = LocalDateTime.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    private final LocalDateTime endOfWeekLocalDateTime = LocalDateTime.now();
    private final LocalDateTime startOfMonthLocalDateTime = LocalDateTime.now().withDayOfMonth(1);
    private final LocalDateTime endOfMonthLocalDateTime = LocalDateTime.now();
    private final PlayerRepository playerRepository;

    public Page<DeckDTO> getAllDecks(Pageable pageable) {
        Page<Deck> deckPage = deckRepository.findAll(pageable);
        return new PageImpl<>(mappingService.mapDecksDTOWithSubObjects(
                deckPage.getContent(), deckCountService.mapDecksObjectWithCardsDeckCounted(deckPage.getContent())),
                pageable,
                deckPage.getTotalElements()
        );
    }

    public DeckDTO getDeckById(UUID id) {
        Deck deck = deckRepository.findById(id).orElseThrow(NoResultByIdException::new);
        return mappingService.mapDeckDTOWithSubObjects(deck, deckCountService.mapCardsWithDeckCount(deck.getCards()));
    }

    public DeckDTO getDeckByName(String name) {
        if (name == null || name.isEmpty()) {
            throw new BadRequestException();
        }
        name = Encode.forHtml(name);
        Deck deck = deckRepository.findByName(name).orElseThrow(NotFindByArgumentException::new);
        return mappingService.mapDeckDTOWithSubObjects(deck, deckCountService.mapCardsWithDeckCount(deck.getCards()));
    }

    public Page<DeckDTO> getDecksLikeByName(String name, Pageable pageable) {
        if (name == null || name.isEmpty()) {
            throw new BadRequestException();
        }
        Page<Deck> deckPage = deckRepository.findByNameContaining(Encode.forHtml(name), pageable);
        return new PageImpl<>(mappingService.mapDecksDTOWithSubObjects(
                deckPage.getContent(), deckCountService.mapDecksObjectWithCardsDeckCounted(deckPage.getContent())),
                pageable,
                deckPage.getTotalElements()
        );
    }

    public Boolean existDeckByName(String name) {
        if (name == null || name.isEmpty()) {
            throw new BadRequestException();
        }
        return deckRepository.existsByName(Encode.forHtml(name));
    }

    public Page<DeckDTO> getDecksByPlayerId(UUID playerId, Pageable pageable) {
        if (playerId == null) {
            throw new BadRequestException();
        }
        Page<Deck> deckPage = deckRepository.findByPlayer_Id(playerId, pageable);
        return new PageImpl<>(mappingService.mapDecksDTOWithSubObjects(
                deckPage.getContent(), deckCountService.mapDecksObjectWithCardsDeckCounted(deckPage.getContent())),
                pageable,
                deckPage.getTotalElements()
        );
    }

    public Page<DeckDTO> getDecksByFactionId(UUID factionId, Pageable pageable) {
        if (factionId == null) {
            throw new BadRequestException();
        }
        Page<Deck> deckPage = deckRepository.findByFaction_Id(factionId, pageable);
        return new PageImpl<>(mappingService.mapDecksDTOWithSubObjects(
                deckPage.getContent(), deckCountService.mapDecksObjectWithCardsDeckCounted(deckPage.getContent())),
                pageable,
                deckPage.getTotalElements()
        );
    }

    public Page<DeckDTO> getDecksByHeroId(UUID heroId, Pageable pageable) {
        if (heroId == null) {
            throw new BadRequestException();
        }
        Page<Deck> deckPage = deckRepository.findByHero_id(heroId, pageable);
        return new PageImpl<>(mappingService.mapDecksDTOWithSubObjects(
                deckPage.getContent(), deckCountService.mapDecksObjectWithCardsDeckCounted(deckPage.getContent())),
                pageable,
                deckPage.getTotalElements()
        );
    }

    public DeckDTO getLastDeckCreatedByFactionId(UUID factionId) {
        if (factionId == null) {
            throw new BadRequestException();
        }
        Deck deck = deckRepository.findFirstByFaction_IdOrderByDateOfCreationDesc(factionId).orElseThrow(NotFindByArgumentException::new);
        return mappingService.mapDeckDTOWithSubObjects(deck, deckCountService.mapCardsWithDeckCount(deck.getCards()));
    }

    public Page<DeckDTO> getLast5DecksCreated(Pageable pageable) {
        Page<Deck> deckPage = deckRepository.findTop5ByOrderByDateOfCreationDesc(pageable);
        return new PageImpl<>(mappingService.mapDecksDTOWithSubObjects(
                deckPage.getContent(), deckCountService.mapDecksObjectWithCardsDeckCounted(deckPage.getContent())),
                pageable,
                deckPage.getTotalElements()
        );
    }

    public Page<DeckDTO> getLast5DecksCreatedByFactionId(UUID factionId, Pageable pageable) {
        if (factionId == null) {
            throw new BadRequestException();
        }
        Page<Deck> deckPage = deckRepository.findTop5ByFaction_IdOrderByDateOfCreationDesc(factionId, pageable);
        return new PageImpl<>(mappingService.mapDecksDTOWithSubObjects(
                deckPage.getContent(), deckCountService.mapDecksObjectWithCardsDeckCounted(deckPage.getContent())),
                pageable,
                deckPage.getTotalElements()
        );
    }

    public DeckDTO getLastDeckCreatedByHeroId(UUID heroId) {
        if (heroId == null) {
            throw new BadRequestException();
        }
        Deck deck = deckRepository.findFirstByHero_IdOrderByDateOfCreationDesc(heroId).orElseThrow(NotFindByArgumentException::new);
        return mappingService.mapDeckDTOWithSubObjects(deck, deckCountService.mapCardsWithDeckCount(deck.getCards()));
    }

    public Page<DeckDTO> getLast5DecksCreatedByHeroId(UUID heroId, Pageable pageable) {
        if (heroId == null) {
            throw new BadRequestException();
        }
        Page<Deck> deckPage = deckRepository.findTop5ByHero_IdOrderByDateOfCreationDesc(heroId, pageable);
        return new PageImpl<>(mappingService.mapDecksDTOWithSubObjects(
                deckPage.getContent(), deckCountService.mapDecksObjectWithCardsDeckCounted(deckPage.getContent())),
                pageable,
                deckPage.getTotalElements()
        );
    }

    public Page<DeckDTO> getDecksCreatedThisDay(Pageable pageable) {
        Page<Deck> deckPage = deckRepository.findByDateOfCreationOrderByDateOfCreationDesc(LocalDate.now(), pageable);
        return new PageImpl<>(mappingService.mapDecksDTOWithSubObjects(
                deckPage.getContent(), deckCountService.mapDecksObjectWithCardsDeckCounted(deckPage.getContent())),
                pageable,
                deckPage.getTotalElements()
        );
    }

    public Page<DeckDTO> getDecksCreatedThisWeek(Pageable pageable) {
        Page<Deck> deckPage = deckRepository.findByDateOfCreationBetweenOrderByDateOfCreationDesc(startOfWeekLocalDate, endOfWeekLocalDate, pageable);
        return new PageImpl<>(mappingService.mapDecksDTOWithSubObjects(
                deckPage.getContent(), deckCountService.mapDecksObjectWithCardsDeckCounted(deckPage.getContent())),
                pageable,
                deckPage.getTotalElements()
        );
    }

    public Page<DeckDTO> getDecksCreatedThisMonth(Pageable pageable) {
        Page<Deck> deckPage = deckRepository.findByDateOfCreationBetweenOrderByDateOfCreationDesc(startOfMonthLocalDate, endOfMonthLocalDate, pageable);
        return new PageImpl<>(mappingService.mapDecksDTOWithSubObjects(
                deckPage.getContent(), deckCountService.mapDecksObjectWithCardsDeckCounted(deckPage.getContent())),
                pageable,
                deckPage.getTotalElements()
        );
    }

    public Page<DeckDTO> getLast5DecksModified(Pageable pageable) {
        Page<Deck> deckPage = deckRepository.findTop5ByOrderByLastModificationDesc(pageable);
        return new PageImpl<>(mappingService.mapDecksDTOWithSubObjects(
                deckPage.getContent(), deckCountService.mapDecksObjectWithCardsDeckCounted(deckPage.getContent())),
                pageable,
                deckPage.getTotalElements()
        );
    }

    public DeckDTO getLastDeckModifiedByFactionId(UUID factionId) {
        if (factionId == null) {
            throw new BadRequestException();
        }
        Deck deck = deckRepository.findFirstByFaction_IdOrderByLastModificationDesc(factionId).orElseThrow(NotFindByArgumentException::new);
        return mappingService.mapDeckDTOWithSubObjects(deck, deckCountService.mapCardsWithDeckCount(deck.getCards()));
    }

    public Page<DeckDTO> getLast5DecksModifiedByFactionId(UUID factionId, Pageable pageable) {
        if (factionId == null) {
            throw new BadRequestException();
        }
        Page<Deck> deckPage = deckRepository.findTop5ByFaction_IdOrderByLastModificationDesc(factionId, pageable);
        return new PageImpl<>(mappingService.mapDecksDTOWithSubObjects(
                deckPage.getContent(), deckCountService.mapDecksObjectWithCardsDeckCounted(deckPage.getContent())),
                pageable,
                deckPage.getTotalElements()
        );
    }

    public DeckDTO getLastDeckModifiedByHeroId(UUID heroId) {
        if (heroId == null) {
            throw new BadRequestException();
        }
        Deck deck = deckRepository.findFirstByHero_IdOrderByLastModificationDesc(heroId).orElseThrow(NotFindByArgumentException::new);
        return mappingService.mapDeckDTOWithSubObjects(deck, deckCountService.mapCardsWithDeckCount(deck.getCards()));
    }

    public Page<DeckDTO> getLast5DecksModifiedByHeroId(UUID heroId, Pageable pageable) {
        if (heroId == null) {
            throw new BadRequestException();
        }
        Page<Deck> deckPage = deckRepository.findTop5ByHero_IdOrderByLastModificationDesc(heroId, pageable);
        return new PageImpl<>(mappingService.mapDecksDTOWithSubObjects(
                deckPage.getContent(), deckCountService.mapDecksObjectWithCardsDeckCounted(deckPage.getContent())),
                pageable,
                deckPage.getTotalElements()
        );
    }

    public Page<DeckDTO> getDecksModifiedThisDay(Pageable pageable) {
        Page<Deck> deckPage = deckRepository.findByLastModificationBetweenOrderByLastModificationDesc(startOfDayLocalDateTime, endOfDayLocalDateTime, pageable);
        return new PageImpl<>(mappingService.mapDecksDTOWithSubObjects(
                deckPage.getContent(), deckCountService.mapDecksObjectWithCardsDeckCounted(deckPage.getContent())),
                pageable,
                deckPage.getTotalElements()
        );
    }

    public Page<DeckDTO> getDecksModifiedThisWeek(Pageable pageable) {
        Page<Deck> deckPage = deckRepository.findByLastModificationBetweenOrderByLastModificationDesc(startOfWeekLocalDateTime, endOfWeekLocalDateTime, pageable);
        return new PageImpl<>(mappingService.mapDecksDTOWithSubObjects(
                deckPage.getContent(), deckCountService.mapDecksObjectWithCardsDeckCounted(deckPage.getContent())),
                pageable,
                deckPage.getTotalElements()
        );
    }

    public Page<DeckDTO> getDecksModifiedThisMonth(Pageable pageable) {
        Page<Deck> deckPage = deckRepository.findByLastModificationBetweenOrderByLastModificationDesc(startOfMonthLocalDateTime, endOfMonthLocalDateTime, pageable);
        return new PageImpl<>(mappingService.mapDecksDTOWithSubObjects(
                deckPage.getContent(), deckCountService.mapDecksObjectWithCardsDeckCounted(deckPage.getContent())),
                pageable,
                deckPage.getTotalElements()
        );
    }

    public Page<DeckDTO> getDecksByTagId(UUID tagId, Pageable pageable) {
        if (tagId == null) {
            throw new BadRequestException();
        }
        Page<Deck> deckPage = deckRepository.findByTags_Id(tagId, pageable);
        return new PageImpl<>(mappingService.mapDecksDTOWithSubObjects(
                deckPage.getContent(), deckCountService.mapDecksObjectWithCardsDeckCounted(deckPage.getContent())),
                pageable,
                deckPage.getTotalElements()
        );
    }

    public Page<DeckDTO> getDecksByTagIdIn(List<UUID> tagIds, Pageable pageable) {
        if (tagIds == null) {
            throw new BadRequestException();
        }
        Page<Deck> deckPage = deckRepository.findByTags_IdIn(tagIds, pageable);
        return new PageImpl<>(mappingService.mapDecksDTOWithSubObjects(
                deckPage.getContent(), deckCountService.mapDecksObjectWithCardsDeckCounted(deckPage.getContent())),
                pageable,
                deckPage.getTotalElements()
        );
    }

    public DeckDTO addDeck(DeckDTO deckDTO) {
        validationService.verifyDeckIntegrity(deckDTO);

        deckDTO.setName(Encode.forHtml(deckDTO.getName()));
        deckDTO.setDateOfCreation(LocalDate.now());
        deckDTO.setLastModification(LocalDateTime.now());
        deckDTO.setIsParticipant(false);

        Player player = playerRepository.findByName(deckDTO.getPlayerName()).orElseThrow(NoResultByIdException::new);

        Deck deck = deckRepository.save(mappingService.mapDeckWithSubObjects(deckDTO, player));

        return mappingService.mapDeckDTOWithSubObjects(deck, deckCountService.mapCardsWithDeckCount(deck.getCards()));
    }

    public DeckDTO updateDeckById(UUID id, DeckDTO deckDTO) {
        if (deckDTO.getId().equals(id)) {
            validationService.verifyDeckIntegrity(deckDTO);

            // Ici spécifiquement, nous ne vérifions pas l'égalité des sous-objets, car ils peuvent tous changer
            // Le player peut changer, une fonctionnalité permettra de copier le deck pour l'avoir dans sa liste
            // de deck.

            Deck deckToUpdate = deckRepository.findById(id).orElseThrow(NoResultByIdException::new);

            Player player = playerRepository.findByName(deckDTO.getPlayerName()).orElseThrow(NoResultByIdException::new);

            Deck deckUpdated = mappingService.mapDeckWithSubObjects(deckDTO, player);

            deckToUpdate.setName(Encode.forHtml(deckUpdated.getName()));
            deckToUpdate.setPlayer(deckUpdated.getPlayer());
            deckToUpdate.setFaction(deckUpdated.getFaction());
            deckToUpdate.setHero(deckUpdated.getHero());
            deckToUpdate.setLastModification(LocalDateTime.now());
            deckToUpdate.setCards(new ArrayList<>(deckUpdated.getCards()));
            deckToUpdate.setTags(deckUpdated.getTags());
            deckToUpdate.setIsParticipant(deckUpdated.getIsParticipant());

            Deck deck = deckRepository.save(deckToUpdate);

            return mappingService.mapDeckDTOWithSubObjects(deck, deckCountService.mapCardsWithDeckCount(deck.getCards()));
        } else {
            throw new IdNotMatchException();
        }
    }

    public void patchIsParticipantByDeckId(UUID id, Boolean isParticipant) {
        Deck deckToUpdate = deckRepository.findById(id).orElseThrow(NoResultByIdException::new);
        deckToUpdate.setIsParticipant(isParticipant);
        Deck deckUpdated = deckRepository.save(deckToUpdate);
        mappingService.mapDeckDTOWithSubObjects(deckUpdated, deckCountService.mapCardsWithDeckCount(deckUpdated.getCards()));
    }

    public void patchIsParticipantByDeckIdIn(List<UUID> deckIds, Boolean isParticipant) {
        List<Deck> decksToUpdate = deckRepository.findByIdIn(deckIds);
        if (decksToUpdate.isEmpty()) {
            throw new NoResultByIdException();
        }
        List<Deck> deckUpdated = decksToUpdate.stream().peek(deck -> deck.setIsParticipant(isParticipant)).toList();
        List<Deck> decks = deckRepository.saveAll(deckUpdated);
        mappingService.mapDecksDTOWithSubObjects(
                decks, deckCountService.mapDecksObjectWithCardsDeckCounted(decks));
    }

    public void deleteDeckById(UUID id) {
        if (!deckRepository.existsById(id)) {
            throw new NoResultByIdException();
        }
        deckRepository.deleteById(id);
    }

    public void deleteDeckNonParticipantByPlayerId(UUID playerId) {
        deckRepository.deleteByPlayer_IdAndIsParticipantFalse(playerId);
    }

}
