package com.drum_village_server.api.service;

import com.drum_village_server.api.domain.Order;
import com.drum_village_server.api.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;

  public void create(Order order) {
    orderRepository.save(order);
  }
}
