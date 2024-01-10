package com.drum_village_server.api.exception;

import lombok.Getter;

@Getter
public class InvalidCard extends DrumVillageException {
  private static final String MESSAGE = "카드 정보가 올바르지 않습니다.";

  public InvalidCard() {
    super(MESSAGE);
  }

  public InvalidCard(String fieldName, String message) {
    super(MESSAGE);
    addValidation(fieldName, message);
  }

  public int getStatusCode() {
    return 400;
  }
}
