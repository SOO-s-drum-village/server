package com.drum_village_server.api.service;

import com.drum_village_server.api.domain.User;
import com.drum_village_server.api.exception.AlreadyExistsEmailException;
import com.drum_village_server.api.repository.UserRepository;
import com.drum_village_server.api.request.Signup;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public void signup(Signup signup) {
    Optional<User> userOptional = userRepository.findByEmail(signup.getEmail());
    if (userOptional.isPresent()) {
      throw new AlreadyExistsEmailException();
    }

    String encryptedPassword = passwordEncoder.encode(signup.getPassword());

    User user = User.builder()
      .name(signup.getName())
      .password(encryptedPassword)
      .email(signup.getEmail())
      .build();

    userRepository.save(user);
  }
}
