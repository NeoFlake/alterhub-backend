package com.alterhub.alterhubbackend.service.impl;

import com.alterhub.alterhubbackend.dto.DeckDTO;
import com.alterhub.alterhubbackend.entity.Deck;
import com.alterhub.alterhubbackend.entity.Player;
import com.alterhub.alterhubbackend.exception.*;
import com.alterhub.alterhubbackend.mapper.DeckMapper;
import com.alterhub.alterhubbackend.repository.DeckRepository;
import com.alterhub.alterhubbackend.repository.PlayerRepository;
import com.alterhub.alterhubbackend.service.interfaces.*;
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
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeckServiceImpl implements DeckService {

    private final DeckRepository deckRepository;
    private final PlayerRepository playerRepository;

    private final FactionService factionService;
    private final HeroService heroService;
    private final CardService cardService;
    private final PlayerService playerService;

    // Permet d'effectuer les appels db avec les dates permettant de cibler correctement les périodes temporelles recherchées
    // Dans ce cas pour taper le paramètre dateOfCreation qui est en LocalDate
    private final LocalDate startOfWeekLocalDate = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    private final LocalDate endOfWeekLocalDate = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
    private final LocalDate startOfMonthLocalDate = LocalDate.now().withDayOfMonth(1);
    private final LocalDate endOfMonthLocalDate = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());
    // Dans celui-ci, on tape sur le paramètre lastModification qui est en LocalDateTime
    private final LocalDateTime startOfDayLocalDateTime = LocalDate.now().atStartOfDay();
    private final LocalDateTime endOfDayLocalDateTime   = LocalDateTime.now();
    private final LocalDateTime startOfWeekLocalDateTime = LocalDateTime.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    private final LocalDateTime endOfWeekLocalDateTime = LocalDateTime.now();
    private final LocalDateTime startOfMonthLocalDateTime = LocalDateTime.now().withDayOfMonth(1);
    private final LocalDateTime endOfMonthLocalDateTime = LocalDateTime.now();

    public Page<DeckDTO> getAllDecks(Pageable pageable) {
        Page<Deck> deckPage = deckRepository.findAll(pageable);
        return new PageImpl<>(mapDecksDTOWithSubObjects(deckPage.getContent()), pageable, deckPage.getTotalElements());
    }

    public DeckDTO getDeckById(UUID id) {
        return mapDeckDTOWithSubObjects(deckRepository.findById(id).orElseThrow(NoResultByIdException::new));
    }

    public DeckDTO getDeckByName(String name) {
        if (name == null || name.isEmpty()) {
            throw new BadRequestException();
        }
        name = Encode.forHtml(name);
        return mapDeckDTOWithSubObjects(deckRepository.findByName(name).orElseThrow(NotFindByArgumentException::new));
    }

    public Page<DeckDTO> getDecksLikeByName(String name, Pageable pageable) {
        if (name == null || name.isEmpty()) {
            throw new BadRequestException();
        }
        Page<Deck> deckPage = deckRepository.findByNameContaining(Encode.forHtml(name), pageable);
        return new PageImpl<>(mapDecksDTOWithSubObjects(deckPage.getContent()), pageable, deckPage.getTotalElements());
    }

    public Page<DeckDTO> getDecksByPlayerId(UUID playerId, Pageable pageable) {
        if(playerId==null){
            throw new BadRequestException();
        }
        Page<Deck> deckPage = deckRepository.findByPlayer_Id(playerId, pageable);
        return new PageImpl<>(mapDecksDTOWithSubObjects(deckPage.getContent()), pageable, deckPage.getTotalElements());
    }

    public Page<DeckDTO> getDecksByFactionId(UUID factionId, Pageable pageable) {
        if(factionId==null){
            throw new BadRequestException();
        }
        Page<Deck> deckPage = deckRepository.findByFaction_Id(factionId, pageable);
        return new PageImpl<>(mapDecksDTOWithSubObjects(deckPage.getContent()), pageable, deckPage.getTotalElements());
    }

    public Page<DeckDTO> getDecksByHeroId(UUID heroId, Pageable pageable) {
        if(heroId==null){
            throw new BadRequestException();
        }
        Page<Deck> deckPage = deckRepository.findByHero_id(heroId, pageable);
        return new PageImpl<>(mapDecksDTOWithSubObjects(deckPage.getContent()), pageable, deckPage.getTotalElements());
    }

    public DeckDTO getLastDeckCreatedByFactionId(UUID factionId) {
        if(factionId==null){
            throw new BadRequestException();
        }
        return mapDeckDTOWithSubObjects(deckRepository.findFirstByFaction_IdOrderByDateOfCreationDesc(factionId).orElseThrow(NotFindByArgumentException::new));
    }

    public Page<DeckDTO> getLast5DecksCreatedByFactionId(UUID factionId, Pageable pageable) {
        if(factionId==null){
            throw new BadRequestException();
        }
        Page<Deck> deckPage = deckRepository.findTop5ByFaction_IdOrderByDateOfCreationDesc(factionId, pageable);
        return new PageImpl<>(mapDecksDTOWithSubObjects(deckPage.getContent()), pageable, deckPage.getTotalElements());
    }

    public DeckDTO getLastDeckCreatedByHeroId(UUID heroId) {
        if(heroId==null){
            throw new BadRequestException();
        }
        return mapDeckDTOWithSubObjects(deckRepository.findFirstByHero_IdOrderByDateOfCreationDesc(heroId).orElseThrow(NotFindByArgumentException::new));
    }

    public Page<DeckDTO> getLast5DecksCreatedByHeroId(UUID heroId, Pageable pageable) {
        if (heroId==null) {
            throw new BadRequestException();
        }
        Page<Deck> deckPage = deckRepository.findTop5ByHero_IdOrderByDateOfCreationDesc(heroId, pageable);
        return new PageImpl<>(mapDecksDTOWithSubObjects(deckPage.getContent()), pageable, deckPage.getTotalElements());
    }

    public Page<DeckDTO> getDecksCreatedThisDay(Pageable pageable) {
        Page<Deck> deckPage = deckRepository.findByDateOfCreationOrderByDateOfCreationDesc(LocalDate.now(), pageable);
        return new PageImpl<>(mapDecksDTOWithSubObjects(deckPage.getContent()), pageable, deckPage.getTotalElements());
    }

    public Page<DeckDTO> getDecksCreatedOThisWeek(Pageable pageable) {
        Page<Deck> deckPage = deckRepository.findByDateOfCreationBetweenOrderByDateOfCreationDesc(startOfWeekLocalDate, endOfWeekLocalDate, pageable);
        return new PageImpl<>(mapDecksDTOWithSubObjects(deckPage.getContent()), pageable, deckPage.getTotalElements());
    }

    public Page<DeckDTO> getDecksCreatedThisMonth(Pageable pageable) {
        Page<Deck> deckPage = deckRepository.findByDateOfCreationBetweenOrderByDateOfCreationDesc(startOfMonthLocalDate, endOfMonthLocalDate, pageable);
        return new PageImpl<>(mapDecksDTOWithSubObjects(deckPage.getContent()), pageable, deckPage.getTotalElements());
    }

    public DeckDTO getLastDeckModifiedByFactionId(UUID factionId){
        if(factionId==null){
            throw new BadRequestException();
        }
        return mapDeckDTOWithSubObjects(deckRepository.findFirstByFaction_IdOrderByLastModificationDesc(factionId).orElseThrow(NotFindByArgumentException::new));
    }

    public Page<DeckDTO> getLast5DecksModifiedByFactionId(UUID factionId, Pageable pageable){
        if(factionId==null){
            throw new BadRequestException();
        }
        Page<Deck> deckPage = deckRepository.findTop5ByFaction_IdOrderByLastModificationDesc(factionId, pageable);
        return new PageImpl<>(mapDecksDTOWithSubObjects(deckPage.getContent()), pageable, deckPage.getTotalElements());
    }

    public DeckDTO getLastDeckModifiedByHeroId(UUID heroId, Pageable pageable){
        if(heroId==null){
            throw new BadRequestException();
        }
        return mapDeckDTOWithSubObjects(deckRepository.findFirstByHero_IdOrderByLastModificationDesc(heroId).orElseThrow(NotFindByArgumentException::new));
    }

    public Page<DeckDTO> getLast5DecksModifiedByHeroId(UUID heroId, Pageable pageable){
        if(heroId==null){
            throw new BadRequestException();
        }
        Page<Deck> deckPage = deckRepository.findTop5ByHero_IdOrderByLastModificationDesc(heroId, pageable);
        return new PageImpl<>(mapDecksDTOWithSubObjects(deckPage.getContent()), pageable, deckPage.getTotalElements());
    }

    public Page<DeckDTO> getDecksModifiedThisDay(Pageable pageable){
        Page<Deck> deckPage = deckRepository.findByLastModificationBetweenOrderByLastModificationDesc(startOfDayLocalDateTime, endOfDayLocalDateTime, pageable);
        return new PageImpl<>(mapDecksDTOWithSubObjects(deckPage.getContent()), pageable, deckPage.getTotalElements());
    }

    public Page<DeckDTO> getDecksModifiedOThisWeek(Pageable pageable){
        Page<Deck> deckPage = deckRepository.findByLastModificationBetweenOrderByLastModificationDesc(startOfWeekLocalDateTime, endOfWeekLocalDateTime, pageable);
        return new PageImpl<>(mapDecksDTOWithSubObjects(deckPage.getContent()), pageable, deckPage.getTotalElements());
    }

    public Page<DeckDTO> getDecksModifiedThisMonth(Pageable pageable){
        Page<Deck> deckPage = deckRepository.findByLastModificationBetweenOrderByLastModificationDesc(startOfMonthLocalDateTime, endOfMonthLocalDateTime, pageable);
        return new PageImpl<>(mapDecksDTOWithSubObjects(deckPage.getContent()), pageable, deckPage.getTotalElements());
    }

    public Page<DeckDTO> getDecksByTagId(UUID tagId, Pageable pageable){
        if(tagId==null){
            throw new BadRequestException();
        }
        Page<Deck> deckPage = deckRepository.findByTags_Id(tagId, pageable);
        return new PageImpl<>(mapDecksDTOWithSubObjects(deckPage.getContent()), pageable, deckPage.getTotalElements());
    }

    public Page<DeckDTO> getDecksByTagIdIn(List<UUID> tagIds, Pageable pageable){
        if(tagIds==null){
            throw new BadRequestException();
        }
        Page<Deck> deckPage = deckRepository.findByTags_IdIn(tagIds, pageable);
        return new PageImpl<>(mapDecksDTOWithSubObjects(deckPage.getContent()), pageable, deckPage.getTotalElements());
    }

    public DeckDTO addDeck(DeckDTO deckDTO) {
        verifyDeckIntegrity(deckDTO);

        deckDTO.setName(Encode.forHtml(deckDTO.getName()));
        deckDTO.setDescription(Encode.forHtml(deckDTO.getDescription()));
        deckDTO.setDateOfCreation(LocalDate.now());
        deckDTO.setLastModification(LocalDateTime.now());
        deckDTO.setIsParticipant(false);

        Deck deck = deckRepository.save(mapDeckWithSubObjects(deckDTO));

        return mapDeckDTOWithSubObjects(deck);
    }

    public DeckDTO updateDeckById(UUID id, DeckDTO deckDTO) {
        if (deckDTO.getId().equals(id)) {
            verifyDeckIntegrity(deckDTO);

            // Ici spécifiquement, nous ne vérifions pas l'égalité des sous-objets, car ils peuvent tous changer
            // Le player peut changer, une fonctionnalité permettra de copier le deck pour l'avoir dans sa liste
            // de deck.

            Deck deckToUpdate = deckRepository.findById(id).orElseThrow(NoResultByIdException::new);
            Deck deckUpdated = mapDeckWithSubObjects(deckDTO);

            deckToUpdate.setName(Encode.forHtml(deckUpdated.getName()));
            deckToUpdate.setDescription(Encode.forHtml(deckUpdated.getDescription()));
            deckToUpdate.setPlayer(deckUpdated.getPlayer());
            deckToUpdate.setFaction(deckUpdated.getFaction());
            deckToUpdate.setHero(deckUpdated.getHero());
            deckToUpdate.setLastModification(LocalDateTime.now());
            deckToUpdate.setCards(deckUpdated.getCards());
            deckToUpdate.setTags(deckUpdated.getTags());
            deckToUpdate.setIsParticipant(deckUpdated.getIsParticipant());

            return mapDeckDTOWithSubObjects(deckRepository.save(deckToUpdate));
        } else {
            throw new IdNotMatchException();
        }
    }

    public void patchIsParticipantByDeckId(UUID id, Boolean isParticipant){
        Deck deckToUpdate = deckRepository.findById(id).orElseThrow(NoResultByIdException::new);
        deckToUpdate.setIsParticipant(isParticipant);
        mapDeckDTOWithSubObjects(deckRepository.save(deckToUpdate));
    }

    public void patchIsParticipantByDeckIdIn(List<UUID> deckIds, Boolean isParticipant){
        List<Deck> decksToUpdate = deckRepository.findByIdIn(deckIds);
        if(decksToUpdate.isEmpty()){
           throw new NoResultByIdException();
        }
        List<Deck> deckUpdated = decksToUpdate.stream().peek(deck -> deck.setIsParticipant(isParticipant)).toList();
        mapDecksDTOWithSubObjects(deckRepository.saveAll(deckUpdated));
    }

    public void deleteDeckById(UUID id) {
        if (!deckRepository.existsById(id)) {
            throw new NoResultByIdException();
        }
        deckRepository.deleteById(id);
    }

    public void deleteDeckNonParticipantByPlayerId(UUID playerId) {
        if (!deckRepository.existsByPlayer_Id(playerId)) {
            throw new NoResultByIdException();
        }
        deckRepository.deleteByPlayer_IdAndIsParticipantFalse(playerId);
    }

    public void validateDeck(DeckDTO deckDTO) {

        Player playerOnBase = playerRepository.findById(deckDTO.getPlayerId()).orElseThrow(NoResultByIdException::new);

        Deck deckReceived = DeckMapper.toEntity(deckDTO, playerOnBase);
        Deck deckOnBase = deckRepository.findById(deckDTO.getId()).orElseThrow(NoResultByIdException::new);

        if (!deckOnBase.getName().equals(deckReceived.getName())
                || !deckOnBase.getDescription().equals(deckReceived.getDescription())
                || !deckOnBase.getDateOfCreation().equals(deckReceived.getDateOfCreation())
                || !deckOnBase.getLastModification().equals(deckReceived.getLastModification())
                || !deckOnBase.getPlayer().getId().equals(playerOnBase.getId())) {
            throw new BadRequestException();
        }

        factionService.validateFaction(deckDTO.getFaction());
        heroService.validateHero(deckDTO.getHero());

        if (deckOnBase.getCards().size() == deckReceived.getCards().size()) {
            deckDTO.getCards().forEach(cardService::validateCard);
        }

    }

    public void verifyDeckIntegrity(DeckDTO deckDTO) {
        if (deckDTO.getName() == null || deckDTO.getName().isEmpty()
                || deckDTO.getDescription() == null || deckDTO.getDescription().isEmpty()
                || deckDTO.getPlayerId() == null
                || deckDTO.getDateOfCreation() == null
                || deckDTO.getLastModification() == null) {
            throw new BadRequestException();
        }

        factionService.verifyFactionIntegrity(deckDTO.getFaction());
        heroService.verifyHeroIntegrity(deckDTO.getHero());

        if (deckDTO.getCards() == null || deckDTO.getCards().isEmpty()) {
            throw new BadRequestException();
        } else if (deckDTO.getCards().size() < 39 || deckDTO.getCards().size() > 59) {
            throw new InvalidDeckSizeException();
        } else {
            deckDTO.getCards().forEach(cardService::verifyCardIntegrity);
        }

    }

    public DeckDTO mapDeckDTOWithSubObjects(Deck deck) {
        return DeckMapper.toDTO(deck, cardService.mapCardsWithDeckCount(deck.getCards()));

    }

    public List<DeckDTO> mapDecksDTOWithSubObjects(List<Deck> decks) {
        return decks.stream()
                .map(this::mapDeckDTOWithSubObjects)
                .toList();
    }

    public Deck mapDeckWithSubObjects(DeckDTO deckDTO) {
        return DeckMapper.toEntity(deckDTO, playerService.mapPlayerWithSubObject(playerService.getPlayerById(deckDTO.getPlayerId())));
    }

    public List<Deck> mapDecksWithSubObjects(List<DeckDTO> decksDTO) {
        return decksDTO.stream()
                .map(this::mapDeckWithSubObjects)
                .toList();
    }

}
