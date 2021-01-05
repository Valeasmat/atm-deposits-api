package com.everis.atmdeposits.dto;


import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CardsResponse {

    private List<Card> cards;
}
