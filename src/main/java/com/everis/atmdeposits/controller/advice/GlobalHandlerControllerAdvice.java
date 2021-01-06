package com.everis.atmdeposits.controller.advice;

import com.everis.atmdeposits.exception.BlacklistException;
import com.everis.atmdeposits.util.ErrorDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalHandlerControllerAdvice {

    @ExceptionHandler(BlacklistException.class)
    public ResponseEntity<ErrorDetail> blacklistException(BlacklistException e){
        return new ResponseEntity<>(new ErrorDetail(e.getMessage(),e.getDate()), HttpStatus.UNAUTHORIZED);
    }
}
