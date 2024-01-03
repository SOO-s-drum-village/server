package com.drum_village_server.api.service;


import com.drum_village_server.api.repository.LectureRepository;
import com.drum_village_server.api.request.LectureSearch;
import com.drum_village_server.api.response.LectureResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureService {

  private final LectureRepository lectureRepository;

  public List<LectureResponse> getList(LectureSearch request) {
    return (request.getCategory() != null)
      ? getLecturesByCategory(request)
      : getAllLectures(request);
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
