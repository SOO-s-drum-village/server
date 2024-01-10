package com.drum_village_server.api.domain.payment;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Card {

  private final String cardNumber;
  private final String expiry;
  private final String birth;
  private final String pwd2digit;

  @Builder
  public Card(String cardNumber, String expiry, String birth, String pwd2digit) {
    this.cardNumber = cardNumber;
    this.expiry = expiry;
    this.birth = birth;
    this.pwd2digit = pwd2digit;
  }
}
