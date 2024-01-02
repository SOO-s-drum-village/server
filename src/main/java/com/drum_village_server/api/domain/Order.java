package com.drum_village_server.api.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  private Integer amount;

  private Boolean isDone;

  @Builder
  public Order(Integer amount) {
    this.amount = amount;
  }

  public Order editIsDone(Boolean isDone) {
    this.isDone = isDone;
    return this;
  }
}
