package com.alterhub.alterhubbackend.constant;

public final class ApiRoutes {

    private ApiRoutes() {
    } // Empêche l’instanciation

    public static final String BASE_API_URL = "/api";
    public static final String SEARCH_BY_ID = "/{id}";
    public static final String SEARCH_BY_NAME = "/{name}";
    public static final String ALTERED_ID = "/alteredid";
    public static final String TODAY = "/today";
    public static final String WEEK = "/week";
    public static final String MONTH = "/month";
    public static final String TYPES = "/types";
    public static final String SUBTYPES = "/subtypes";
    public static final String RARITIES = "/rarities";
    public static final String FACTIONS = "/factions";
    public static final String SETS = "/sets";
    public static final String CARDS = "/cards";
    public static final String HEROS = "/heros";
    public static final String DECKS = "/decks";
    public static final String NAME = "/name";
    public static final String PLAYERS = "/players";
    public static final String TAGS = "/tags";
    public static final String PARTICIPANTS = "/participants";
    public static final String TOURNAMENTS = "/tournaments";
    public static final String USERS = "/users";

    public static final class Types {
        public static final String ROOT = BASE_API_URL + TYPES;
    }

    public static final class SubTypes {
        public static final String ROOT = BASE_API_URL + SUBTYPES;
    }

    public static final class Rarities {
        public static final String ROOT = BASE_API_URL + RARITIES;
    }

    public static final class Factions {
        public static final String ROOT = BASE_API_URL + FACTIONS;
    }

    public static final class Sets {
        public static final String ROOT = BASE_API_URL + SETS;
    }

    public static final class Cards {
        public static final String ROOT = BASE_API_URL + CARDS;
        public static final String BY_ALTERED_ID = ALTERED_ID + SEARCH_BY_ID;
        public static final String BY_TYPE_ID = TYPES + SEARCH_BY_ID;
        public static final String BY_SUBTYPE_ID = SUBTYPES + SEARCH_BY_ID;
        public static final String BY_RARITY_ID = RARITIES + SEARCH_BY_ID;
        public static final String BY_FACTION_ID = FACTIONS + SEARCH_BY_ID;
    }

    public static final class Heros {
        public static final String ROOT = BASE_API_URL + HEROS;
        public static final String BY_NAME = NAME + SEARCH_BY_NAME;
        public static final String BY_FACTION_ID = FACTIONS + SEARCH_BY_ID;
    }

    public static final class Decks {
        public static final String CREATED = "/created";
        public static final String MODIFIED = "/modified";
        public static final String LAST = "/last";
        public static final String FIVE_LATEST = "/five";
        public static final String ROOT = BASE_API_URL + DECKS;
        public static final String BY_NAME = NAME + SEARCH_BY_NAME;
        public static final String BY_LIKE_BY_NAME = BY_NAME + "/like";
        public static final String EXIST_BY_NAME = NAME + "/exist" + SEARCH_BY_NAME;
        public static final String BY_PLAYER_ID = PLAYERS + SEARCH_BY_ID;
        public static final String BY_FACTION_ID = FACTIONS + SEARCH_BY_ID;
        public static final String BY_HERO_ID = HEROS + SEARCH_BY_ID;
        public static final String LATEST_CREATED_BY_FACTION_ID = CREATED + LAST + FACTIONS + SEARCH_BY_ID;
        public static final String FIVE_LATEST_CREATED_BY_FACTION_ID = CREATED + FIVE_LATEST + FACTIONS + SEARCH_BY_ID;
        public static final String LATEST_CREATED_BY_HERO_ID = CREATED + LAST + HEROS + SEARCH_BY_ID;
        public static final String FIVE_LATEST_CREATED_BY_HERO_ID = CREATED + FIVE_LATEST + HEROS + SEARCH_BY_ID;
        public static final String CREATED_TODAY = CREATED + TODAY;
        public static final String CREATED_THIS_WEEK = CREATED + WEEK;
        public static final String CREATED_THIS_MONTH = CREATED + MONTH;
        public static final String LATEST_MODIFIED_BY_FACTION_ID = MODIFIED + LAST + FACTIONS + SEARCH_BY_ID;
        public static final String FIVE_LATEST_MODIFIED_BY_FACTION_ID = MODIFIED + FIVE_LATEST + FACTIONS + SEARCH_BY_ID;
        public static final String LATEST_MODIFIED_BY_HERO_ID = MODIFIED + LAST + HEROS + SEARCH_BY_ID;
        public static final String FIVE_LATEST_MODIFIED_BY_HERO_ID = MODIFIED + FIVE_LATEST + HEROS + SEARCH_BY_ID;
        public static final String MODIFIED_TODAY = MODIFIED + TODAY;
        public static final String MODIFIED_THIS_WEEK = MODIFIED + WEEK;
        public static final String MODIFIED_THIS_MONTH = MODIFIED + MONTH;
        public static final String BY_TAG_ID = TAGS + SEARCH_BY_ID;
        public static final String BY_TAG_ID_IN = TAGS + "/in";
    }

    public static final class Tags {
        public static final String ROOT = BASE_API_URL + TAGS;
        public static final String BY_NAME = NAME + SEARCH_BY_NAME;
    }

    public static final class Participants {
        public static final String ROOT = BASE_API_URL + PARTICIPANTS;
        public static final String BY_PLAYER_ID = PLAYERS + SEARCH_BY_ID;
        public static final String BY_TOURNAMENT_ID = TOURNAMENTS + SEARCH_BY_ID;
        public static final String BY_CLASSEMENT = "/classement" + "/{classement}";
        public static final String BY_DECK_FACTION_ID = DECKS + FACTIONS + SEARCH_BY_ID;
        public static final String BY_DECK_FACTION_ID_IN = DECKS + FACTIONS + "/in";
        public static final String BY_DECK_HERO_ID = DECKS + HEROS + SEARCH_BY_ID;
        public static final String BY_DECK_HERO_ID_IN = DECKS + HEROS + "/in";
        public static final String BY_DECK_TAG_ID = DECKS + TAGS + SEARCH_BY_ID;
        public static final String BY_DECK_TAG_ID_IN = DECKS + TAGS + "/in";
    }

    public static final class Tournaments {
        public static final String NUMBER_OF_PLAYERS = "/numberofplayers";
        public static final String DATE = "/date";
        public static final String PLAYED = "/played";
        public static final String ROOT = BASE_API_URL + TOURNAMENTS;
        public static final String BY_NAME = NAME + SEARCH_BY_NAME;
        public static final String LESS_THAN_NUMBER_OF_PLAYER = NUMBER_OF_PLAYERS + "/lessthan/{numberofplayers}";
        public static final String GREATER_THAN_NUMBER_OF_PLAYER = NUMBER_OF_PLAYERS + "/greaterthan/{numberofplayers}";
        public static final String BETWEEN_A_RANGE_OF_PLAYER = NUMBER_OF_PLAYERS + "/between";
        public static final String BY_DATE = DATE + "/{date}";
        public static final String BEFORE_A_DATE = DATE + "/before/{date}";
        public static final String AFTER_A_DATE = DATE + "/after/{date}";
        public static final String BETWEEN_A_RANGE_OF_DATE = DATE + "/between";
        public static final String BY_PLAYER_ID = PLAYERS + SEARCH_BY_ID;
        public static final String BY_FACTION_ID =  FACTIONS + SEARCH_BY_ID;
        public static final String BY_FACTION_ID_IN = FACTIONS + "/in";
        public static final String BY_HERO_ID = HEROS + SEARCH_BY_ID;
        public static final String BY_HERO_ID_IN = HEROS + "/in";
        public static final String PLAYED_THIS_WEEK = PLAYED + WEEK;
        public static final String PLAYED_THIS_MONTH = PLAYED + MONTH;
        public static final String BY_LOCATION = "/location/{location}";
    }

    public static final class Players {
        public static final String ROOT = BASE_API_URL + PLAYERS;
        public static final String BY_USER_ID = USERS + SEARCH_BY_ID;
        public static final String BY_NAME = NAME + SEARCH_BY_NAME;
    }

    public static final class Users {
        public static final String ROOT = BASE_API_URL + USERS;
        public static final String AUTHENTICATION = "/authentication";
        public static final String ACCESS_GRANTED = "/access";
        public static final String REFRESH_TOKEN = "/token/refresh";
        public static final String LOGOUT = "/logout";
        public static final String REGISTER = "/register";
    }

}
