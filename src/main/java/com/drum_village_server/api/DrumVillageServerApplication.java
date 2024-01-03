package com.drum_village_server.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class DrumVillageServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(DrumVillageServerApplication.class, args);
  }
}
