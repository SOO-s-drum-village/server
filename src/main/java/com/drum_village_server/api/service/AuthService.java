package com.drum_village_server.api.service;

import com.drum_village_server.api.domain.User;
import com.drum_village_server.api.domain.payment.Payment;
import com.drum_village_server.api.domain.payment.PaymentCreator;
import com.drum_village_server.api.exception.AlreadyExistsEmailException;
import com.drum_village_server.api.exception.PaymentNotFound;
import com.drum_village_server.api.repository.PaymentRepository;
import com.drum_village_server.api.repository.UserRepository;
import com.drum_village_server.api.request.Signup;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final PaymentRepository paymentRepository;
  private final PasswordEncoder passwordEncoder;
  private final PaymentService paymentService;
  private final OrderService orderService;

  @Transactional
  public void signup(Signup signup) {
    Optional<User> userOptional = userRepository.findByEmail(signup.getEmail());
    if (userOptional.isPresent()) {
      throw new AlreadyExistsEmailException();
    }

    paymentService.verifyCard(signup.toCard());

    String encryptedPassword = passwordEncoder.encode(signup.getPassword());

    User user = User.builder()
      .name(signup.getName())
      .password(encryptedPassword)
      .email(signup.getEmail())
      .build();

    userRepository.save(user);

    PaymentCreator paymentCreator = PaymentCreator.builder()
      .user(user)
      .cardNumber(signup.getCardNumber())
      .cardExpiry(signup.getCardExpiry())
      .birth(signup.getBirth())
      .cardPwd2digit(signup.getCardPwd2digit())
      .cardCvc(signup.getCardCvc())
      .build();

    paymentService.create(paymentCreator);

    Payment payment = paymentRepository.findByUserId(user.getId())
      .orElseThrow(PaymentNotFound::new);

    LocalDateTime now = LocalDateTime.now();
    orderService.reserveOrder(payment, now.plusWeeks(2));
  }
}
