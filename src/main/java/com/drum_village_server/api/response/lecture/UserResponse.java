package com.drum_village_server.api.response.lecture;

import com.drum_village_server.api.domain.User;
import lombok.Getter;

@Getter
public class UserResponse {
  private final Long id;
  private final String name;
  private final String email;

  public UserResponse(User user) {
    this.id = user.getId();
    this.name = user.getName();
    this.email = user.getEmail();
  }
}
