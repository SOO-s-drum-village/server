package com.drum_village_server.api.repository;

import com.drum_village_server.api.domain.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
  Optional<Payment> findByUserId(Long userId);
}
