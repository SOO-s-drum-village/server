package com.drum_village_server.api.domain;

import com.drum_village_server.api.domain.enums.OrderType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "merchant_uid")
  private String merchantUid;

  private Integer amount;

  @Enumerated(EnumType.STRING)
  private OrderType type;

  @Column(name = "schedule_at")
  private LocalDateTime scheduleAt;

  @Column(name = "description")
  private String description;

  @CreatedDate
  @Column(name = "created_at", columnDefinition = "timestamp with time zone not null")
  private LocalDateTime createdAt = LocalDateTime.now();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Builder
  public Order(String merchantUid, Integer amount, OrderType type, LocalDateTime scheduleAt, String description, User user) {
    this.merchantUid = merchantUid;
    this.amount = amount;
    this.type = type;
    this.scheduleAt = scheduleAt;
    this.description = description;
    this.user = user;
  }
}
