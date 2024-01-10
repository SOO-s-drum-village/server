package com.drum_village_server.api.common;

import lombok.Getter;

@Getter
public class Utils {
  public static String formatCardNumber(String cardNumber) {
    return cardNumber.replaceAll("(.{4})(?!$)", "$1-");
  }


  public static String formatCardExpiry(String cardExpiry) {
      String year = "20" +  cardExpiry.substring(2);
      String month =cardExpiry.substring(0, 2);
      return year + "-" + month;
  }
}
