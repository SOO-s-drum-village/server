package com.drum_village_server.api.service;


import com.drum_village_server.api.domain.Lecture;
import com.drum_village_server.api.exception.LectureNotFound;
import com.drum_village_server.api.repository.LectureRepository;
import com.drum_village_server.api.request.LectureSearch;
import com.drum_village_server.api.response.lecture.LectureDetailResponse;
import com.drum_village_server.api.response.lecture.LectureResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LectureService {

  private final LectureRepository lectureRepository;

  public List<LectureResponse> getList(LectureSearch request) {
    return (request.getCategory() != null)
      ? getLecturesByCategory(request)
      : getAllLectures(request);
  }
  public LectureDetailResponse get(Long lectureId) {
    Optional<Lecture> lecture = lectureRepository.findById(lectureId);
    if (lecture.isEmpty()) {
      throw new LectureNotFound();
    }

    return new LectureDetailResponse(lecture.get());
  }

  private List<LectureResponse> getLecturesByCategory(LectureSearch request) {
    return lectureRepository.findByCategories_NameIn(List.of(request.getCategory()), request.toPageable())
      .stream()
      .map(LectureResponse::new)
      .toList();
  }


  private List<LectureResponse> getAllLectures(LectureSearch request) {
    return lectureRepository.findAll(request.toPageable())
      .stream()
      .map(LectureResponse::new)
      .toList();
  }
}
