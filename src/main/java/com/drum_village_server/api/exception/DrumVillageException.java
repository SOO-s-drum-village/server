package com.drum_village_server.api.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class DrumVillageException extends RuntimeException {
  public final Map<String, String> validation = new HashMap<>();

  public DrumVillageException(String message) {
    super(message);
  }

  public DrumVillageException(String message, Throwable cause) {
    super(message, cause);
  }

  public abstract int getStatusCode();

  public void addValidation(String fieldName, String message) {
    validation.put(fieldName, message);
  }
}