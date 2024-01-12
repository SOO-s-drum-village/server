package com.drum_village_server.api.controller;

import com.drum_village_server.api.config.UserPrincipal;
import com.drum_village_server.api.domain.User;
import com.drum_village_server.api.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api-ssmusicweb.shop", uriPort = 443)
@ExtendWith(RestDocumentationExtension.class)
class UserControllerDocTest {

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
  @DisplayName("내 정보 조회")
  @WithMockUser(username = "daniel@twosun.com", roles = {"USER"}, password = "a123456!")
  void getMe() throws Exception {
    // given
    User user = User.builder()
      .name("daniel")
      .email("daniel@twosun.com")
      .password("$40801$ufESlsydxCx+FfJdMTOK9CifGsVYR8unIAVvsROADnlE9GTMeqr/GXJ8Aj80egQPg51ZBHhtsBzjayP9018lTQ==$aPs5pzIxeL7c7zeCWir5/oNWGVEJZwbrrHI8EwZvmUg=")
      .build();

    userRepository.save(user);

    UserDetails userDetails = new UserPrincipal(user);

    SecurityContextHolder.getContext().setAuthentication(
      new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities())
    );

    //expected
    this.mockMvc.perform(get("/users/me")
        .contentType(APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andDo(document("get-me",
        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
        responseFields(
          fieldWithPath("id").type(JsonFieldType.NUMBER).description("유저 ID"),
          fieldWithPath("name").type(JsonFieldType.STRING).description("유저 이름"),
          fieldWithPath("email").type(JsonFieldType.STRING).description("유저 이메일")
        )
      ));
  }
}