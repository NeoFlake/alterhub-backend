package com.alterhub.alterhubbackend.exception;

import com.alterhub.alterhubbackend.constant.ErrorMessages;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoResultByIdException extends RuntimeException{

    public NoResultByIdException() {
        super(ErrorMessages.NOT_FOUND_BY_ID);
    }

}
