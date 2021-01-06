package com.everis.atmdeposits.util;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
@ApiModel("Error")
public class ErrorDetail {
    @ApiModelProperty(value = "message error",example = "Client is registered in a blacklist")
    private String message;
    @ApiModelProperty(value = "date error")
    private Instant date;
}
