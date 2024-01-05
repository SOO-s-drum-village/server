package com.drum_village_server.api.domain.enums;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnumDocs {
  Map<String,String> LectureEnumCategory;
  Map<String,String> SortDirection;
}
