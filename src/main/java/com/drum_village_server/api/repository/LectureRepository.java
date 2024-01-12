package com.drum_village_server.api.repository;

import com.drum_village_server.api.domain.Lecture;
import com.drum_village_server.api.domain.enums.LectureCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LectureRepository extends JpaRepository<Lecture, Long> {

  @Override
  @Query("select l from Lecture l join fetch l.categories")
  Page<Lecture> findAll(Pageable pageable);

  @Query("select l from Lecture l join fetch l.categories")
  Page<Lecture> findByCategories_NameIn(List<LectureCategory> categories, Pageable pageable);
}

