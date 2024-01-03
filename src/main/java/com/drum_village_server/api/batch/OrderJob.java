package com.drum_village_server.api.batch;

import com.drum_village_server.api.config.JobLoggerListener;
import com.drum_village_server.api.domain.Order;
import com.drum_village_server.api.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Collections;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class OrderJob {

  private final JobRepository jobRepository;
  private final PlatformTransactionManager transactionManager;
  private final OrderRepository orderRepository;


  @Bean
  public Job simpleJob1(Step step1) {
    return new JobBuilder("step111111" + System.currentTimeMillis(), jobRepository)
      .listener(new JobLoggerListener())
      .start(step1)
      .build();
  }

  @Bean
  public Step step1(ItemReader orderReader, ItemProcessor orderProcessor, ItemWriter orderWriter) {
    return new StepBuilder("orderStep", jobRepository)
      .chunk(3, transactionManager)
      .reader(orderReader)
      .processor(orderProcessor)
      .writer(orderWriter)
      .build();
  }

  @Bean
  public ItemWriter<Order> orderWriter() {
    return new RepositoryItemWriterBuilder<Order>()
      .repository(orderRepository)
      .methodName("save")
      .build();
  }


  @Bean
  public ItemProcessor<Order, Order> orderProcessor() {
    return item -> item.editIsDone(true);
  }

  @Bean
  public RepositoryItemReader<Order> orderReader() {
    return new RepositoryItemReaderBuilder<Order>()
      .name("repositoryItemReader")
      .repository(orderRepository)
      .methodName("findAll")
      .pageSize(1)
      .arguments(List.of())
      .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
      .build();
  }
}