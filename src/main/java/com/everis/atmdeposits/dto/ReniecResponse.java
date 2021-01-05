package com.everis.atmdeposits.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReniecResponse {

    private String entityName;
    private Boolean success;
}
