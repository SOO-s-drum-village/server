package com.drum_village_server.api.domain.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum OrderType implements EnumType {
  RESERVATION("예약"),
  COMPLETION("완료"),
  FAIL("실패");

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
