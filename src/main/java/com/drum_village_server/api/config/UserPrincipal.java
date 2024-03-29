package com.drum_village_server.api.config;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public class UserPrincipal extends User {

  private final Long userId;

  public UserPrincipal(com.drum_village_server.api.domain.User user) {
    super(user.getEmail(), user.getPassword(),
      List.of(
        new SimpleGrantedAuthority("ROLE_USER")
      ));
    this.userId = user.getId();
  }

  public Long getUserId() {
    return userId;
  }
}
