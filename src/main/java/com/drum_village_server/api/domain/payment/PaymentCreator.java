package com.drum_village_server.api.domain.payment;


import com.drum_village_server.api.common.Utils;
import com.drum_village_server.api.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentCreator {
  private final User user;
  private final String cardNumber;
  private final String cardExpiry;
  private final String birth;
  private final String cardPwd2digit;
  private final String cardCvc;

  @Builder
  public PaymentCreator(User user, String cardNumber, String cardExpiry, String brith, String cardPwd2digit, String cardCvc) {
    this.user = user;
    this.cardNumber = Utils.formatCardNumber(cardNumber);
    this.cardExpiry = Utils.formatCardExpiry(cardExpiry);
    this.birth = brith;
    this.cardPwd2digit = cardPwd2digit;
    this.cardCvc = cardCvc;
  }
}
