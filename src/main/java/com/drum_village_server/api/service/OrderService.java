package com.drum_village_server.api.service;

import com.drum_village_server.api.domain.Order;
import com.drum_village_server.api.repository.OrderRepository;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;
  private final PaymentService paymentService;

  public void create(Order order) {
    orderRepository.save(order);
  }

  public void pay() {
    String accessToken = paymentService.getAccessToken();

    Gson str = new Gson();
    RestTemplate rt = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setBearerAuth(accessToken);

    JsonObject scheduleJsonObject = new JsonObject();
    scheduleJsonObject.addProperty("merchant_uid", "aaaaaa");
    scheduleJsonObject.addProperty("schedule_at", 1704779015215L);
    scheduleJsonObject.addProperty("currency", "KRW");
    scheduleJsonObject.addProperty("amount", 1000);

    JsonArray jsonArr = new JsonArray();
    jsonArr.add(scheduleJsonObject);

    JsonObject reqJson = new JsonObject();
    reqJson.addProperty("customer_uid", "aaa");
    reqJson.addProperty("card_number", "5570-4297-1413-3097");
    reqJson.addProperty("expiry", "2029-10");
    reqJson.addProperty("birth", "910710");
    reqJson.addProperty("pwd_2digit", "00");
    reqJson.addProperty("cvc", "681");
    reqJson.add("schedules",jsonArr);
    String json = str.toJson(reqJson);

    HttpEntity<String> entity = new HttpEntity<>(json, headers);

    ResponseEntity<Map> response = rt.exchange(
      "https://api.iamport.kr/subscribe/payments/schedule",
      HttpMethod.POST,
      entity,
      Map.class
    );

    log.info(">>>>>>>>>>>>>>>>>>>{}",response);

  }


}
