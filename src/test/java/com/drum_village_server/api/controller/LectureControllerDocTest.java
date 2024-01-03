package com.drum_village_server.api.controller;

import com.drum_village_server.api.domain.Lecture;
import com.drum_village_server.api.domain.LectureCategory;
import com.drum_village_server.api.domain.enums.LectureEnumCategory;
import com.drum_village_server.api.repository.LectureCategoryRepository;
import com.drum_village_server.api.repository.LectureRepository;
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

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api-ssmusicweb.com", uriPort = 443)
@ExtendWith(RestDocumentationExtension.class)
class LectureControllerDocTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private LectureRepository lectureRepository;

  @Autowired
  private LectureCategoryRepository lectureCategoryRepository;

  @Test
  @DisplayName("강의 목록 조회")
  void getLectureList() throws Exception {
    //given
    Lecture lecture1 = Lecture.builder()
      .title("테스트 제목 1")
      .level(1)
      .lectureUrl("www.sample-lecture-url.com")
      .imageUrl("www.sample-image-url.com")
      .build();

    Lecture lecture2 = Lecture.builder()
      .title("테스트 제목 2")
      .level(2)
      .lectureUrl("www.sample-lecture-url.com")
      .imageUrl("www.sample-image-url.com")
      .build();

    Lecture lecture3 = Lecture.builder()
      .title("테스트 제목 3")
      .level(3)
      .lectureUrl("www.sample-lecture-url.com")
      .imageUrl("www.sample-image-url.com")
      .build();

    lectureRepository.saveAll(List.of(lecture1, lecture2, lecture3));

    LectureCategory lectureCategory1 = LectureCategory.builder()
      .name(LectureEnumCategory.BASIC)
      .lecture(lecture1)
      .build();

    LectureCategory lectureCategory2 = LectureCategory.builder()
      .name(LectureEnumCategory.K_POP)
      .lecture(lecture1)
      .build();

    LectureCategory lectureCategory3 = LectureCategory.builder()
      .name(LectureEnumCategory.PRAISE)
      .lecture(lecture2)
      .build();

    LectureCategory lectureCategory4 = LectureCategory.builder()
      .name(LectureEnumCategory.BASIC)
      .lecture(lecture3)
      .build();

    lectureCategoryRepository.saveAll(List.of(lectureCategory1, lectureCategory2, lectureCategory3, lectureCategory4));

    //expected
    this.mockMvc.perform(get("/lectures?page=1&size=2")
        .contentType(APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andDo(document("get-lecture-list",
        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
        queryParameters(
          parameterWithName("page").description("페이지 번호").optional()
            .attributes(key("type").value("Number")),
          parameterWithName("size").description("조회 개수").optional()
            .attributes(key("type").value("Number"))
        ),
        responseFields(
          fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("강의 ID"),
          fieldWithPath("[].title").type(JsonFieldType.STRING).description("강의 제목"),
          fieldWithPath("[].level").type(JsonFieldType.NUMBER).description("강의 레벨"),
          fieldWithPath("[].categories").type(JsonFieldType.ARRAY).description("강의 카테고리 목록")
        )
      ));
  }
}