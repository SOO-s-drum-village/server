package com.drum_village_server.api.controller;

import com.drum_village_server.api.request.LectureSearch;
import com.drum_village_server.api.response.LectureResponse;
import com.drum_village_server.api.service.LectureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LectureController {

  private final LectureService lectureService;

  @GetMapping("/lectures")
  public List<LectureResponse> getList(@ModelAttribute LectureSearch request) {
    request.validate();
    return lectureService.getList(request);
  }
}