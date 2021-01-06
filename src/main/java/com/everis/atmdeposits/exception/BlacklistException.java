package com.everis.atmdeposits.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class BlacklistException extends Exception{

    private String message;
    private Instant date;

    public BlacklistException(String message){
        this.message=message;
        this.date=Instant.now();
    }
}
