package com.everis.atmdeposits.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FingerprintResponse {

    private String entityName;
    private Boolean success;
}
