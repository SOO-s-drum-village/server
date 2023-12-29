package com.drum_village_server.api.config;


import com.drum_village_server.api.domain.Order;
import com.drum_village_server.api.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class JobConfig {

  private final OrderRepository orderRepository;
  @Bean
  public Job simpleJob1(JobRepository jobRepository, Step simpleStep1) {
    return new JobBuilder("simpleJob", jobRepository)
      .listener(new JobLoggerListener())
      .start(simpleStep1)
      .build();
  }
  @Bean
  public Step simpleStep1(JobRepository jobRepository, ItemReader ordersReader, PlatformTransactionManager transactionManager){
    return new StepBuilder("simpleStep1", jobRepository)
      .chunk(2, transactionManager)
      .reader(new ListItemReader<>(Arrays.asList("data1", "data2", "data3", "data4", "data5", "data6", "data7", "data8", "data9", "data10")))
      .processor(new ItemProcessor<String, String>() {
        @Override
        public String process(String data) throws Exception {
          System.out.println("data = " + data);
          return data + "_A";
        }
      })
      .writer(new ItemWriter<String>() {
        @Override
        public void write(List<String> items) throws Exception {
          items.forEach(System.out::println);
        }
      })
      .build();
  }

  @Bean
  public RepositoryItemReader<Order> ordersReader() {
    return new RepositoryItemReaderBuilder<Order>()
      .name("ordersReader")
      .repository(orderRepository)
      .methodName("findAll")
      .pageSize(2)
      .arguments(Arrays.asList())
      .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
      .build();
  }
}