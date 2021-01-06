package com.everis.atmdeposits.controller.advice;


import com.everis.atmdeposits.exception.BlacklistException;
import com.everis.atmdeposits.util.ErrorDetail;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class GlobalHandlerControllerAdviceTest {

    @InjectMocks
    private GlobalHandlerControllerAdvice handlerControllerAdvice;

    @Test
    public void testBlacklistException(){
        BlacklistException exception=new BlacklistException("error message");
        ResponseEntity<ErrorDetail> result=handlerControllerAdvice.blacklistException(exception);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(exception.getMessage(),result.getBody().getMessage());
        Assertions.assertEquals(exception.getDate(),result.getBody().getDate());
    }
}
