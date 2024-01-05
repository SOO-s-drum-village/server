package com.drum_village_server.api.domain.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SortDirection implements EnumType {
  ASC("오름차순"),
  DESC("내림차순");

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
