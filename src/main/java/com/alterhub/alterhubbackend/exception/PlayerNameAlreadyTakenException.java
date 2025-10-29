package com.alterhub.alterhubbackend.exception;

import com.alterhub.alterhubbackend.constant.ErrorMessages;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class PlayerNameAlreadyTakenException extends RuntimeException {
    public PlayerNameAlreadyTakenException() {
        super(ErrorMessages.PLAYER_NAME_ALREADY_TAKEN);
    }
}
