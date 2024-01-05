package com.drum_village_server.api.domain;

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
  private com.drum_village_server.api.domain.enums.LectureCategory name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "lecture_id", nullable = false)
  private Lecture lecture;

  @Builder
  public LectureCategory(Lecture lecture, com.drum_village_server.api.domain.enums.LectureCategory name) {
    this.name = name;
    this.lecture = lecture;
  }
}
