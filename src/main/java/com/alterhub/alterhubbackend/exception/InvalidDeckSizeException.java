package com.alterhub.alterhubbackend.exception;

import com.alterhub.alterhubbackend.constant.ErrorMessages;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidDeckSizeException extends RuntimeException {
    public InvalidDeckSizeException(){
        super(ErrorMessages.INVALID_DECK_SIZE_LIBELLE);
    }
}
