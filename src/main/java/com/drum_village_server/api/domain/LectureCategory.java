package com.drum_village_server.api.domain;

import com.drum_village_server.api.domain.enums.LectureEnumCategory;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "lecture_categories")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LectureCategory {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "name")
  @Enumerated(EnumType.STRING)
  private LectureEnumCategory name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "lecture_id", nullable = false)
  private Lecture lecture;

  @Builder
  public LectureCategory(Lecture lecture, LectureEnumCategory name) {
    this.name = name;
    this.lecture = lecture;
  }
}
