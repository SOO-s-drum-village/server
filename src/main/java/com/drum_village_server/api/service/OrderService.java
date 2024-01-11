package com.drum_village_server.api.service;

import com.drum_village_server.api.domain.Order;
import com.drum_village_server.api.domain.enums.OrderType;
import com.drum_village_server.api.domain.payment.Payment;
import com.drum_village_server.api.repository.OrderRepository;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

  private final static int PAY_AMOUNT = 1000;
  private final OrderRepository orderRepository;
  private final PaymentService paymentService;

  public void create(Order order) {
    orderRepository.save(order);
  }

  @Transactional
  public void reserveOrder(Payment payment, LocalDateTime scheduleAt) {
    String accessToken = paymentService.getAccessToken();

    Gson str = new Gson();
    RestTemplate rt = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setBearerAuth(accessToken);

    Long userId = payment.getUser().getId();
    long now = new Date().getTime();

    String merchantUid = userId.toString() + "_" + now + "_" + UUID.randomUUID();

    JsonArray schedules = createSchedules(merchantUid, scheduleAt);
    JsonObject reqJson = createPaymentJson(payment, schedules);
    String json = str.toJson(reqJson);

    HttpEntity<String> entity = new HttpEntity<>(json, headers);

    ResponseEntity<Map> response = rt.exchange(
      "https://api.iamport.kr/subscribe/payments/schedule",
      HttpMethod.POST,
      entity,
      Map.class
    );

    Integer responseCode = (Integer) response.getBody().get("code");
    String responseMessage = (String) response.getBody().get("message");

    OrderType orderType = responseCode == 0 ? OrderType.RESERVATION : OrderType.FAIL;

    Order order = Order.builder()
      .merchantUid(merchantUid)
      .amount(PAY_AMOUNT)
      .type(orderType)
      .description(responseMessage)
      .user(payment.getUser())
      .scheduleAt(scheduleAt)
      .build();

    orderRepository.save(order);
  }

  private JsonArray createSchedules(String merchantUid, LocalDateTime scheduleAt) {
    ZonedDateTime zdt = ZonedDateTime.of(scheduleAt, ZoneId.systemDefault());
    long date = zdt.toInstant().toEpochMilli();

    JsonObject scheduleJsonObject = new JsonObject();
    scheduleJsonObject.addProperty("merchant_uid", merchantUid);
    scheduleJsonObject.addProperty("schedule_at", date);
    scheduleJsonObject.addProperty("currency", "KRW");
    scheduleJsonObject.addProperty("amount", 1000);

    JsonArray jsonArr = new JsonArray();
    jsonArr.add(scheduleJsonObject);

    return jsonArr;
  }

  private JsonObject createPaymentJson(Payment payment, JsonArray schedules) {
    JsonObject paymentJson = new JsonObject();
    paymentJson.addProperty("customer_uid", payment.getUser().getId().toString());
    paymentJson.addProperty("card_number", payment.getCardNumber());
    paymentJson.addProperty("expiry", payment.getCardExpiry());
    paymentJson.addProperty("birth", payment.getBirth());
    paymentJson.addProperty("pwd_2digit", payment.getCardPwd2digit());
    paymentJson.addProperty("cvc", payment.getCardCvc());
    paymentJson.add("schedules", schedules);

    return paymentJson;
  }


}
