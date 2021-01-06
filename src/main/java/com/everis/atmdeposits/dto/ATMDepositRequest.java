package com.everis.atmdeposits.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ATMDepositRequest {
    @ApiModelProperty(notes = "Client's document number",example = "10000000")
    private String documentNumber;
}
