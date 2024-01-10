package com.drum_village_server.api.domain.payment;

import com.drum_village_server.api.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "payments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "card_number")
  private String cardNumber;

  @Column(name = "card_expiry")
  private String cardExpiry;

  @Column(name = "brith")
  private String brith;

  @Column(name = "card_pwd_2digit")
  private String cardPwd2digit;

  @Column(name = "card_cvc")
  private String cardCvc;

  @Column(name = "subscription")
  private Boolean subscription = true;

  @CreatedDate
  @Column(name = "created_at", columnDefinition = "timestamp with time zone not null")
  private LocalDateTime createdAt = LocalDateTime.now();

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "users_id", nullable = false)
  private User user;

  @Builder
  public Payment(PaymentCreator paymentCreator) {
    this.user = paymentCreator.getUser();
    this.cardNumber = paymentCreator.getCardNumber();
    this.cardExpiry = paymentCreator.getCardExpiry();
    this.brith = paymentCreator.getBrith();
    this.cardPwd2digit = paymentCreator.getCardPwd2digit();
    this.cardCvc = paymentCreator.getCardCvc();
  }
}
