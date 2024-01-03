package com.drum_village_server.api.repository;

import com.drum_village_server.api.domain.Lecture;
import com.drum_village_server.api.domain.enums.LectureEnumCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
  Page<Lecture> findByCategories_NameIn(List<LectureEnumCategory> categories, Pageable pageable);
}

