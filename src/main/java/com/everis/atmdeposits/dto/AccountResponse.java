package com.everis.atmdeposits.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AccountResponse {
    private String accountNumber;
    private Long amount;
}
