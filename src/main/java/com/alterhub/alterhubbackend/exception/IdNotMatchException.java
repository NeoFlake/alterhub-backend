package com.alterhub.alterhubbackend.exception;

import com.alterhub.alterhubbackend.constant.ErrorMessages;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IdNotMatchException extends RuntimeException {

    public IdNotMatchException(){
        super(ErrorMessages.ID_NOT_MATCH);
    }

}
