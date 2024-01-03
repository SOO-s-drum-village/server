package com.drum_village_server.api.repository;

import com.drum_village_server.api.domain.LectureCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureCategoryRepository extends JpaRepository<LectureCategory, Long> {
}

