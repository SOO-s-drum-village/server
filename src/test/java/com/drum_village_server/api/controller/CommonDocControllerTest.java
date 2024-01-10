package com.drum_village_server.api.controller;

import com.drum_village_server.api.domain.enums.EnumDocs;
import com.drum_village_server.api.response.EnumResponse;
import com.drum_village_server.api.restdocs.CustomResponseFieldsSnippet;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.PayloadSubsectionExtractor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.attributes;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api-ssmusicweb.shop", uriPort = 443)
@ExtendWith(RestDocumentationExtension.class)
class CommonDocControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void enums() throws Exception {
    // 요청
    ResultActions result = this.mockMvc.perform(
      get("/enums")
        .contentType(MediaType.APPLICATION_JSON)
    );

    // 결과값
    MvcResult mvcResult = result.andReturn();

    // 데이터 파싱
    EnumDocs enumDocs = getData(mvcResult);

    // 문서화 진행
    result.andExpect(status().isOk())
      .andDo(document("custom-response",
        customResponseFields("custom-response", beneathPath("data.lectureEnumCategory").withSubsectionId("lectureEnumCategory"),
          attributes(key("title").value("lectureEnumCategory")),
          enumConvertFieldDescriptor((enumDocs.getLectureEnumCategory()))
        ),
        customResponseFields("custom-response", beneathPath("data.sortDirection").withSubsectionId("sortDirection"),
          attributes(key("title").value("sortDirection")),
          enumConvertFieldDescriptor((enumDocs.getSortDirection()))
          )
      ));
  }

  public static CustomResponseFieldsSnippet customResponseFields
    (String type,
     PayloadSubsectionExtractor<?> subsectionExtractor,
     Map<String, Object> attributes, FieldDescriptor... descriptors) {
    return new CustomResponseFieldsSnippet(type, subsectionExtractor, Arrays.asList(descriptors), attributes
      , true);
  }

  private static FieldDescriptor[] enumConvertFieldDescriptor(Map<String, String> enumValues) {
    return enumValues.entrySet().stream()
      .map(x -> fieldWithPath(x.getKey()).description(x.getValue()))
      .toArray(FieldDescriptor[]::new);
  }

  private EnumDocs getData(MvcResult result) throws IOException {
    EnumResponse<EnumDocs> enumResponse = objectMapper
      .readValue(result.getResponse().getContentAsByteArray(),
        new TypeReference<EnumResponse<EnumDocs>>() {}
      );
    return enumResponse.getData();
  }

}