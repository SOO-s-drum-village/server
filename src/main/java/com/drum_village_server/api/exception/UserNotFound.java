package com.drum_village_server.api.exception;

public class UserNotFound extends DrumVillageException {
  private static final String MESSAGE = "존재하지 않는 유저입니다.";

  public UserNotFound() {
    super(MESSAGE);
  }

  @Override
  public int getStatusCode() {
    return 404;
  }
}
