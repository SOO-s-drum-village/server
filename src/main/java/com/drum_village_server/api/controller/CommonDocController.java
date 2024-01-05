package com.drum_village_server.api.controller;

import com.drum_village_server.api.domain.enums.EnumDocs;
import com.drum_village_server.api.domain.enums.EnumType;
import com.drum_village_server.api.domain.enums.LectureCategory;
import com.drum_village_server.api.response.EnumResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CommonDocController {

  @GetMapping("/enums")
  public EnumResponse<EnumDocs> findEnums() {

    Map<String, String> lectureEumCategory = getDocs(LectureCategory.values());

    return EnumResponse.of(EnumDocs.builder()
      .LectureEnumCategory(lectureEumCategory)
      .build()
    );
  }

  private Map<String, String> getDocs(EnumType[] enumTypes) {
    return Arrays.stream(enumTypes)
      .collect(Collectors.toMap(EnumType::getName, EnumType::getDescription));
  }
}
