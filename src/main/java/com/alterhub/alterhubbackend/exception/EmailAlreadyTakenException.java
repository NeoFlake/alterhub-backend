package com.alterhub.alterhubbackend.exception;

import com.alterhub.alterhubbackend.constant.ErrorMessages;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class EmailAlreadyTakenException extends RuntimeException {
    public EmailAlreadyTakenException() {
        super(ErrorMessages.EMAIL_ALREADY_TAKEN);
    }
}
