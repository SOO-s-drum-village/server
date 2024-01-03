package com.drum_village_server.api.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Table(name = "lectures")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Lecture {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "title")
  private String title;

  @Column(name = "level")
  private Integer level;

  @Column(name = "lecture_url")
  private String lectureUrl;

  @Column(name = "image_url")
  private String imageUrl;

  @OneToMany(mappedBy = "lecture",cascade = CascadeType.REMOVE)
  private List<LectureCategory> categories;

  @CreatedDate
  @Column(name = "created_at", columnDefinition = "timestamp with time zone not null")
  private LocalDateTime createdAt = LocalDateTime.now();

  @Builder
  public Lecture(String title, Integer level, String lectureUrl, String imageUrl) {
    this.title = title;
    this.level = level;
    this.lectureUrl = lectureUrl;
    this.imageUrl = imageUrl;
  }
}
