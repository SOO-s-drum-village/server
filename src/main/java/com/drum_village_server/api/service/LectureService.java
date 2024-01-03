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
    System.out.println(request.getCategory());
    if (request.getCategory() != null) {
      return lectureRepository.findByCategories(request.getCategory(), request.toPageable())
        .stream()
        .map(LectureResponse::new)
        .toList();
    } else {
      return lectureRepository.findAll(request.toPageable())
        .stream()
        .map(LectureResponse::new)
        .toList();
    }
  }
}
