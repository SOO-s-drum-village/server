package com.drum_village_server.api.response;

import com.drum_village_server.api.domain.Lecture;
import com.drum_village_server.api.domain.LectureCategory;
import com.drum_village_server.api.domain.enums.LectureEnumCategory;
import lombok.Getter;

import java.util.List;

@Getter
public class LectureResponse {
  private final Long id;
  private final String title;
  private final Integer level;
  private final List<LectureEnumCategory> categories;

  public LectureResponse(Lecture lecture) {
    this.id = lecture.getId();
    this.title = lecture.getTitle();
    this.level = lecture.getLevel();
    this.categories = lecture.getCategories().stream().map(LectureCategory::getName).toList();
  }
}
