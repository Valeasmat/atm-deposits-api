package com.everis.atmdeposits.dto;


import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ATMDepositResponse {
    private String fingerprintEntityName;
    private List<AccountNumber> validAccounts;
    private Long customerAmount;
}
