package com.drum_village_server.api;

import com.drum_village_server.api.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EnableConfigurationProperties(AppConfig.class)
public class DrumVillageServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(DrumVillageServerApplication.class, args);
  }
}
