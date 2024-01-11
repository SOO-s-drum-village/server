package com.drum_village_server.api.domain;

import com.drum_village_server.api.domain.payment.Payment;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "email")
  private String email;

  @Column(name = "password")
  private String password;

  @Column(name = "is_delted")
  private Boolean isDeleted = false;

  @CreatedDate
  @Column(name = "created_at", columnDefinition = "timestamp with time zone not null")
  private LocalDateTime createdAt = LocalDateTime.now();

  @OneToOne(mappedBy = "user",cascade = CascadeType.REMOVE)
  private Payment payment;

  @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE)
  private List<Order> orders;

  @Builder
  public User(String name, String email, String password) {
    this.name = name;
    this.email = email;
    this.password = password;
    this.createdAt = LocalDateTime.now();
  }
}
