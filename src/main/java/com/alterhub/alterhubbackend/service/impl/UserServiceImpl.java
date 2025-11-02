package com.alterhub.alterhubbackend.service.impl;

import com.alterhub.alterhubbackend.dto.PlayerDTO;
import com.alterhub.alterhubbackend.dto.UserAuthenticationDTO;
import com.alterhub.alterhubbackend.dto.UserDTO;
import com.alterhub.alterhubbackend.dto.UserRequestDTO;
import com.alterhub.alterhubbackend.entity.Player;
import com.alterhub.alterhubbackend.entity.User;
import com.alterhub.alterhubbackend.exception.*;
import com.alterhub.alterhubbackend.mapper.UserMapper;
import com.alterhub.alterhubbackend.repository.PlayerRepository;
import com.alterhub.alterhubbackend.repository.UserRepository;
import com.alterhub.alterhubbackend.service.interfaces.DeckService;
import com.alterhub.alterhubbackend.service.interfaces.PlayerService;
import com.alterhub.alterhubbackend.service.interfaces.RoleService;
import com.alterhub.alterhubbackend.service.interfaces.UserService;
import com.alterhub.alterhubbackend.validation.ValidationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.owasp.encoder.Encode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PlayerService playerService;
    private final PasswordEncoder passwordEncoder;

    private final RoleService roleService;
    private final DeckService deckService;
    private final PlayerRepository playerRepository;

    private final ValidationService validationService;

    public User getUserByIdInternalUsage(UUID id) {
        return userRepository.findById(id).orElseThrow(NoResultByIdException::new);
    }

    public UserDTO getUserById(UUID id) {
        User user = getUserByIdInternalUsage(id);
        return UserMapper.toDto(user);
    }

    @Transactional
    public UserDTO addUser(UserRequestDTO userRequestDTO) {
        validationService.verifyUserRequestIntegrity(userRequestDTO);

        UserRequestDTO userDTOToSave = new UserRequestDTO();
        Player playerToSave;

        // Formatage de l'ensemble des paramètres entrés par l'utilisateur en front pour
        // éviter les injections
        userDTOToSave.setLastName(Encode.forHtml(userRequestDTO.getLastName()));
        userDTOToSave.setFirstName(Encode.forHtml(userRequestDTO.getFirstName()));
        userDTOToSave.setEmail(Encode.forHtml(userRequestDTO.getEmail()));
        userDTOToSave.setPassword(Encode.forHtml(userRequestDTO.getPassword()));
        userDTOToSave.setPlayerName(Encode.forHtml(userRequestDTO.getPlayerName()));
        userDTOToSave.setDateOfCreation(LocalDate.now());
        userDTOToSave.setLastModification(LocalDateTime.now());

        validationService.validatePasswordStrength(userRequestDTO.getPassword());

        // Cryptage du mot de passe pour le préparer à la pousse en base
        userDTOToSave.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));

        // Vérification dans la base de donnée si l'email est déjà utilisé
        if(userRepository.existsByEmail(userDTOToSave.getEmail())) {
            throw new EmailAlreadyTakenException();
        }

        // Vérification dans la base de donnée si le nom du joueur est déjà utilisé
        if(playerService.existsByName(userDTOToSave.getPlayerName())) {
            Player player = playerService.getPlayerByNameInternalUsage(userRequestDTO.getPlayerName());
            // Malheureusement, c'est déjà le cas ; on ne peut donc pas continuer la création de l'utilisateur
            if(player.getUser() != null) {
                throw new PlayerNameAlreadyTakenException();
            } else {
                playerToSave = player;
            }
        } else {
            // Le Player n'existe pas en base de donnée ; on va donc le créer avant
            // de pousser l'utilisateur qui a obligatoirement besoin d'un Player
            // pour exister.
            PlayerDTO playerDTO = new PlayerDTO();
            playerDTO.setName(userDTOToSave.getPlayerName());
            playerDTO.setDecks(null);
            playerDTO.setParticipants(null);
            playerDTO.setUserId(null);

            playerService.addPlayer(playerDTO);

            // Maintenant qu'il est créé, on va pouvoir le récupérer avec son id pour l'envoyer
            // À l'utilisateur et continuer le processus.
            playerToSave = playerService.getPlayerByNameInternalUsage(userRequestDTO.getPlayerName());
        }

        User userSaved =  userRepository.save(UserMapper.toEntityFromRequestDTO(userDTOToSave, playerToSave));

        playerService.setLinkBetweenUserAndPlayer(userSaved, playerToSave.getId());

        return  UserMapper.toDto(userSaved);

    }

    public UserDTO authentication(UserAuthenticationDTO userAuthenticationDTO) {
        validationService.verifyUserAuthenticationIntegrity(userAuthenticationDTO);
        userAuthenticationDTO.setEmail(Encode.forHtml(userAuthenticationDTO.getEmail()));
        userAuthenticationDTO.setPassword(Encode.forHtml(userAuthenticationDTO.getPassword()));
        if(!userRepository.existsByEmail(userAuthenticationDTO.getEmail())) {
            throw new NotFindByArgumentException();
        }

        User userOnBase = userRepository.findByEmail(userAuthenticationDTO.getEmail());

        if(!passwordEncoder.matches(userAuthenticationDTO.getPassword(), userOnBase.getPassword())){
            throw new BadRequestException();
        }

        return UserMapper.toDto(userOnBase);
    }

    public Boolean accessGranted(UserRequestDTO userRequestDTO) {
        validationService.verifyUserRequestIntegrity(userRequestDTO);
        validationService.validateRequestUser(userRequestDTO);
        return roleService.permissionGrantedForUser(userRequestDTO.getId());
    }

    @Transactional
    public UserDTO updateUserById(UUID id, UserRequestDTO userRequestDTO) {
        if(userRequestDTO.getId().equals(id)) {
            validationService.verifyUserRequestIntegrity(userRequestDTO);

            // On encode les informations en provenance du front pour éviter
            // les injections malveillantes.
            userRequestDTO.setLastName(Encode.forHtml(userRequestDTO.getLastName()));
            userRequestDTO.setFirstName(Encode.forHtml(userRequestDTO.getFirstName()));
            userRequestDTO.setPlayerName(Encode.forHtml(userRequestDTO.getPlayerName()));
            userRequestDTO.setEmail(Encode.forHtml(userRequestDTO.getEmail()));
            userRequestDTO.setPassword(Encode.forHtml(userRequestDTO.getPassword()));

            Player playerLinkedWithThatPlayerName = playerRepository.findByName(userRequestDTO.getPlayerName()).orElseThrow(NoResultByIdException::new);

            // On vérifie ensuite que le nom du joueur correspond bien à celui en base
            if(!playerLinkedWithThatPlayerName.getUser().getId().equals(userRequestDTO.getId())) {
                throw new IdNotMatchException();
            }

            // On fait de même avec l'email pour voir s'il est identique ou différent (et dans ce cas on check s'il est déjà pris)
            if(userRepository.existsByEmail(userRequestDTO.getEmail()) && !userRepository.findByEmail(userRequestDTO.getEmail()).getId().equals(userRequestDTO.getId())) {
                throw new BadRequestException();
            }

            // On récupère l'utilisateur en base avec l'id fourni dans le DTO transmis
            User userOnBase = userRepository.findById(userRequestDTO.getId()).orElseThrow(NoResultByIdException::new);

            // Ensuite, vérification que le mot de passe correspond bien à celui en base (sinon on refuse)
            // Obligation de passer le mot de passe en paramètre pour pouvoir modifier son compte.
            if(!passwordEncoder.matches(userRequestDTO.getPassword(), userOnBase.getPassword())){
                throw new BadRequestException();
            }

            // Enfin, on vérifie si l'utilisateur souhaite modifier son mot de passe ; et dans ce cas-là,
            // on applique les mêmes règles de vérification que pour le mot de passe classique
            // pour enfin le placer dans le champ mot de passe standard.
            if(userRequestDTO.getNewPassword() != null){
                if(userRequestDTO.getNewPassword().isEmpty() || userRequestDTO.getNewPassword().equals(userRequestDTO.getPassword())) {
                    throw new BadRequestException();
                }
                validationService.validatePasswordStrength(userRequestDTO.getNewPassword());

                userRequestDTO.setPassword(passwordEncoder.encode(userRequestDTO.getNewPassword()));

            }

            userRequestDTO.setLastModification(LocalDateTime.now());

            return UserMapper.toDto(userRepository.save(UserMapper.toEntityFromRequestDTO(userRequestDTO, playerLinkedWithThatPlayerName)));

        } else {
            throw new IdNotMatchException();
        }
    }

    @Transactional
    public void deleteUserById(UUID id, UserAuthenticationDTO userAuthenticationDTO) {
        if(!userRepository.existsById(id)){
            throw new NoResultByIdException();
        }

        if(!userRepository.existsByEmail(userAuthenticationDTO.getEmail())){
            throw new BadRequestException();
        } else {
            User userOnBase = userRepository.findByEmail(userAuthenticationDTO.getEmail());
            if(!passwordEncoder.matches(userAuthenticationDTO.getPassword(), userOnBase.getPassword())){
                throw new BadRequestException();
            }
        }

        // Suppression des decks qui ne sont pas relié à un tournoi par la table des participants :
        // isParticipant = false
        deckService.deleteDeckNonParticipantByPlayerId(playerService.getPlayerByUserId(id).getId());

        // Puis, on délie le player de son utilisateur associé en plaçant l'user associé à null
        playerService.unsetUserFromPlayer(id);

        // On supprime également son role dans la table associé
        roleService.deleteRoleByUserId(id);

        // Enfin, on supprime proprement l'user
        userRepository.deleteById(id);
    }

}
