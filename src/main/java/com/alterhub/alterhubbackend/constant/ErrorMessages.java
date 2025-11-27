package com.alterhub.alterhubbackend.constant;

public final class ErrorMessages {

    private ErrorMessages(){} // Permet d'éviter l'instanciation involontaire

    public static final String NOT_FOUND_BY_ID = "Aucun résultat trouvé avec cet identifiant";
    public static final String BAD_REQUEST_LIBELLE = "Erreur dans votre requête, veuillez reformuler";
    public static final String NOT_FOUND_BY_ARGUMENT = "Aucun résultat trouvé avec votre requête";
    public static final String INVALID_DECK_SIZE_LIBELLE = "Le nombre de carte dans votre deck est invalide";
    public static final String PASSWORD_WEAKNESS_DETECTED = "Le mot de passe souhaité est trop faible pour nos critères minimum d'acceptabilité";
    public static final String PLAYER_NAME_ALREADY_TAKEN = "Le nom de joueur que vous souhaitez et déjà utilisé";
    public static final String EMAIL_ALREADY_TAKEN = "Votre requête n'a pas pu aboutir, veuillez recommencer";
    public static final String REFRESH_TOKEN_EXPIRED = "Reconnexion nécessaire pour continuer à utiliser nos services";
    public static final String CREATE_AUTH_TOKEN_TRY_OVERFLOW = "Échec lors de l'authentification, veuillez recommencer";

}
