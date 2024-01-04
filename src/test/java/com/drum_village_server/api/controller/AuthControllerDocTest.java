package com.drum_village_server.api.controller;

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
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api-ssmusicweb.com", uriPort = 443)
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
      "email", "daniel@twosun.com"
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
          fieldWithPath("password").type(JsonFieldType.STRING).description("유저 패스워드"),
          fieldWithPath("email").type(JsonFieldType.STRING).description("유저 이메일")
        )
      ));
  }
}