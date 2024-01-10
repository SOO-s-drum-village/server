package com.drum_village_server.api.controller;

import com.drum_village_server.api.domain.User;
import com.drum_village_server.api.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api-ssmusicweb.shop", uriPort = 443)
@ExtendWith(RestDocumentationExtension.class)
class AuthControllerDocTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private UserRepository userRepository;

  @BeforeEach
  void clean() {;
    userRepository.deleteAll();
  }

  @Test
  @DisplayName("회원 가입")
  void singup() throws Exception {
    // given
    String request = objectMapper.writeValueAsString(Map.of(
      "name", "Daniel Im",
      "password", "a123456!",
      "email", "daniel@twosun.com",
      "cardNumber","5365106424464397",
      "cardExpiry","1028",
      "brith","910710",
      "cardPwd2digit","00",
      "cardCvc","047"
    ));

    //expected
    this.mockMvc.perform(post("/auth/signup")
        .contentType(APPLICATION_JSON)
        .content(request)
      )
      .andExpect(status().isOk())
      .andDo(document("signup",
        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
        requestFields(
          fieldWithPath("name").type(JsonFieldType.STRING).description("유저 이름"),
          fieldWithPath("email").type(JsonFieldType.STRING).description("유저 이메일"),
          fieldWithPath("password").type(JsonFieldType.STRING).description("유저 패스워드"),
          fieldWithPath("cardNumber").type(JsonFieldType.STRING).description("카드 번호 16자리"),
          fieldWithPath("cardExpiry").type(JsonFieldType.STRING).description("카드 유효기간 4자리( Month / Year )"),
          fieldWithPath("brith").type(JsonFieldType.STRING).description("생년 월일 6자리"),
          fieldWithPath("cardPwd2digit").type(JsonFieldType.STRING).description("카드 비밀번호 앞 2자리"),
          fieldWithPath("cardCvc").type(JsonFieldType.STRING).description("카드 CVC번호 6자리")
        )
      ));
  }

  @Test
  @DisplayName("로그인")
  void login() throws Exception {
    // given
    User user = User.builder()
      .name("daniel")
      .email("daniel@twosun.com")
      .password("$40801$ufESlsydxCx+FfJdMTOK9CifGsVYR8unIAVvsROADnlE9GTMeqr/GXJ8Aj80egQPg51ZBHhtsBzjayP9018lTQ==$aPs5pzIxeL7c7zeCWir5/oNWGVEJZwbrrHI8EwZvmUg=")
      .build();

    userRepository.save(user);

    String request = objectMapper.writeValueAsString(Map.of(
      "email", "daniel@twosun.com",
      "password", "a123456!"
    ));

    //expected
    this.mockMvc.perform(post("/auth/login/email")
        .contentType(APPLICATION_JSON)
        .content(request)
      )
      .andExpect(status().isOk())
      .andDo(document("login-email",
        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
        requestFields(
          fieldWithPath("email").type(JsonFieldType.STRING).description("유저 이메일"),
          fieldWithPath("password").type(JsonFieldType.STRING).description("유저 패스워드")
        )
      ));
  }

  @Test
  @DisplayName("로그아웃")
  void logout() throws Exception {
    //expected
    this.mockMvc.perform(post("/auth/logout")
        .contentType(APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andDo(document("logout",
        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
        Preprocessors.preprocessResponse(Preprocessors.prettyPrint())
      ));
  }
}