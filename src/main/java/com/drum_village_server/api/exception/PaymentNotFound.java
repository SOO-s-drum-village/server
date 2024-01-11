package com.drum_village_server.api.exception;

public class PaymentNotFound extends DrumVillageException {
  private static final String MESSAGE = "결제 정보가 존재하지 않습니다.";

  public PaymentNotFound() {
    super(MESSAGE);
  }

  @Override
  public int getStatusCode() {
    return 404;
  }
}
