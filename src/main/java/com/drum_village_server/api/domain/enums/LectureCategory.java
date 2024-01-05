package com.drum_village_server.api.domain.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum LectureCategory implements EnumType {
  BASIC("기본"),
  PRAISE("찬양"),
  K_POP("한국가요");

  private final String description;

  @Override
  public String getDescription() {
    return this.description;
  }

  @Override
  public String getName() {
    return this.name();
  }
}
