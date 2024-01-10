package com.drum_village_server.api.exception;

public class LectureNotFound extends DrumVillageException {
  private static final String MESSAGE = "존재하지 않는 강의입니다.";

  public LectureNotFound() {
    super(MESSAGE);
  }

  @Override
  public int getStatusCode() {
    return 404;
  }
}
