package com.everis.atmdeposits.dto;


import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PersonResponse {
    private Integer id;
    private String document;
    private Boolean fingerprint;
    private Boolean blacklist;
}
