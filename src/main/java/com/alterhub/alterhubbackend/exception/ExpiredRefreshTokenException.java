package com.alterhub.alterhubbackend.exception;

import com.alterhub.alterhubbackend.constant.ErrorMessages;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class ExpiredRefreshTokenException extends RuntimeException {

    public ExpiredRefreshTokenException(){
        super(ErrorMessages.REFRESH_TOKEN_EXPIRED);
    }

}
