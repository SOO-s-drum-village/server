package com.drum_village_server.api.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {
  @Scheduled(cron = "*/5 * * * * *")
  public void cronTest() {
    System.out.println(System.currentTimeMillis());
  }
}
