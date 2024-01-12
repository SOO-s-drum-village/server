package com.drum_village_server.api.service;

import com.drum_village_server.api.domain.User;
import com.drum_village_server.api.exception.UserNotFound;
import com.drum_village_server.api.repository.UserRepository;
import com.drum_village_server.api.response.lecture.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public UserResponse get(Long userId) {
    User user = userRepository.findById(userId)
      .orElseThrow(UserNotFound::new);

    return new UserResponse(user);
  }
}
