package com.drum_village_server.api.request;

import com.drum_village_server.api.domain.enums.LectureEnumCategory;
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

  public Pageable toPageable() {
    return PageRequest.of(page, size, Sort.by(direction, sortBy));
  }
}
