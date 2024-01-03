package com.drum_village_server.api.batch;

import com.drum_village_server.api.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderScheduler {

  private final JobLauncher jobLauncher;
  private final OrderJob orderJob;

  @Scheduled(cron = "*/5 * * * * *")
  public void cronTest() {
    try {
      RepositoryItemReader<Order> reader = orderJob.orderReader();
      ItemProcessor<Order, Order> processor = orderJob.orderProcessor();
      ItemWriter<Order> writer = orderJob.orderWriter();

      Step step = orderJob.step1(reader, processor, writer);

      JobParameters jobParameters = new JobParametersBuilder()
        .addLong("time", System.currentTimeMillis())
        .toJobParameters();

      jobLauncher.run(orderJob.simpleJob1(step), jobParameters);

      System.out.println(System.currentTimeMillis());
    } catch (JobExecutionAlreadyRunningException | JobRestartException |
             JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
      // Handle specific exceptions here
      e.printStackTrace();
    } catch (Exception e) {
      // Handle other exceptions here
      e.printStackTrace();
    }
  }
}
