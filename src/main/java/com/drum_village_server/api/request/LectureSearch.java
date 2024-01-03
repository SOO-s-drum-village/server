package com.drum_village_server.api.request;

import com.drum_village_server.api.domain.enums.LectureEnumCategory;
import com.drum_village_server.api.exception.InvalidRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@AllArgsConstructor
public class LectureSearch {

  private Integer page;
  private Integer size;
  private String sortBy;
  private LectureEnumCategory category;
  private Sort.Direction direction;

  public LectureSearch() {
    this.page = 1;
    this.size = 10;
    this.sortBy = "level";
    this.category = null;
    this.direction = Sort.Direction.DESC;
  }

  public void validate() {
    if (page <= 0) {
      throw new InvalidRequest("page", "페이지 번호는 1 이상으로 입력해 주세요.");
    }
  }

  public Pageable toPageable() {
    return PageRequest.of(page - 1 , size, Sort.by(direction, sortBy));
  }
}
