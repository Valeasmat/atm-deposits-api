package com.everis.atmdeposits.exception;


public class BlacklistException extends Exception{
    public BlacklistException(String errorMessage,Throwable err){
        super(errorMessage,err);
    }
}
