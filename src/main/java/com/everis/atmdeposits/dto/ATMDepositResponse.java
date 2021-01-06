package com.everis.atmdeposits.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(description = "Model representing client's info given its document number")
public class ATMDepositResponse {
    @ApiModelProperty(notes = "The entity name to validate fingerprint",example = "Core",position = 1)
    private String fingerprintEntityName;
    @ApiModelProperty(notes = "List of client's active accounts",example = "[\"1111222233334441\",\"1111222233334442\",\"1111222233334443\"]",position = 2)
    private List<AccountNumber> validAccounts;
    @ApiModelProperty(notes = "Total amount among all client's active accounts",example = "3000",position = 3)
    private Long customerAmount;
}
