package com.drum_village_server.api.controller;

import com.drum_village_server.api.domain.User;
import com.drum_village_server.api.domain.payment.Card;
import com.drum_village_server.api.domain.payment.Payment;
import com.drum_village_server.api.domain.payment.PaymentCreator;
import com.drum_village_server.api.request.LectureSearch;
import com.drum_village_server.api.response.lecture.LectureDetailResponse;
import com.drum_village_server.api.response.lecture.LectureResponse;
import com.drum_village_server.api.service.LectureService;
import com.drum_village_server.api.service.OrderService;
import com.drum_village_server.api.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LectureController {

  private final LectureService lectureService;
  private final OrderService orderService;
  private final PaymentService paymentService;

  @GetMapping("/lectures")
  public List<LectureResponse> getList(@ModelAttribute LectureSearch request) {
    request.validate();
    return lectureService.getList(request);
  }

  @GetMapping("/lectures/{lectureId}")
  public LectureDetailResponse get(@PathVariable(name = "lectureId") Long lectureId) {
    return lectureService.get(lectureId);
  }

  @GetMapping("/test")
  public void test() {
    User user = User.builder()
      .password("a123456!")
      .email("chorales@naver.com")
      .name("임성준")
      .build();

    PaymentCreator paymentCreator = PaymentCreator.builder()
      .birth("910710")
      .cardPwd2digit("00")
      .cardNumber("5365-1064-2446-4397")
      .cardExpiry("2028-10")
      .cardCvc("047")
      .user(user)
      .build();

    Payment payment = Payment.builder()
      .paymentCreator(paymentCreator)
      .build();

    orderService.reserveOrder(payment, LocalDateTime.now());
  }

}