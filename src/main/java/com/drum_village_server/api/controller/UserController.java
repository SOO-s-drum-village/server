package com.drum_village_server.api.controller;

import com.drum_village_server.api.config.UserPrincipal;
import com.drum_village_server.api.response.lecture.UserResponse;
import com.drum_village_server.api.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping("/users/me")
  public UserResponse get(@AuthenticationPrincipal UserPrincipal userPrincipal) {
    return userService.get(userPrincipal.getUserId());
  }
}
