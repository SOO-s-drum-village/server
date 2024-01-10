package com.drum_village_server.api.service;

import com.drum_village_server.api.config.AppConfig;
import com.drum_village_server.api.domain.payment.Card;
import com.drum_village_server.api.domain.payment.Payment;
import com.drum_village_server.api.domain.payment.PaymentCreator;
import com.drum_village_server.api.exception.InvalidCard;
import com.drum_village_server.api.repository.PaymentRepository;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

  private final AppConfig appConfig;
  private final PaymentRepository paymentRepository;

  public void create(PaymentCreator paymentCreator) {
    Payment payment = Payment.builder()
      .paymentCreator(paymentCreator)
      .build();

    paymentRepository.save(payment);
  }

  public String getAccessToken() {
    RestTemplate rt = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();

    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("imp_key",appConfig.portOne.get("imp_key"));
    params.add("imp_secret",appConfig.portOne.get("imp_secret"));

    HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

    ResponseEntity<Map> response = rt.exchange(
      "https://api.iamport.kr/users/getToken",
      HttpMethod.POST,
      entity,
      Map.class
    );

    Map<String, Object> responseMap = (Map<String, Object>) response.getBody().get("response");
    return (String)responseMap.get("access_token");
  }

  public void verifyCard(Card card) {
    log.info(">>>>>>>>>>>>{}", card.getExpiry());
    String accessToken = getAccessToken();

    RestTemplate rt = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setBearerAuth(accessToken);

    JsonObject reqJson = new JsonObject();
    reqJson.addProperty("card_number", card.getCardNumber());
    reqJson.addProperty("expiry", card.getExpiry());
    reqJson.addProperty("birth", card.getBirth());
    reqJson.addProperty("pwd_2digit", card.getPwd2digit());


    Gson str = new Gson();
    String json = str.toJson(reqJson);

    HttpEntity<String> entity = new HttpEntity<>(json, headers);

    ResponseEntity<Map> response = rt.exchange(
      "https://api.iamport.kr/subscribe/customers/aaaaa",
      HttpMethod.POST,
      entity,
      Map.class
    );

    log.info(">>>>>>>>>>>>{}", response);

    Integer responseCode = (Integer) response.getBody().get("code");

    if (responseCode != 0) {
      throw new InvalidCard();
    }
  }
}
