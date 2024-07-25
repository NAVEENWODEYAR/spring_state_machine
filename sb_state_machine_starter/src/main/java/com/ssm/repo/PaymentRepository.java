package com.ssm.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ssm.entity.Payment;

/**
 * @author Naveen K Wodeyar
 * @date 25-Jul-2024
 */
public interface PaymentRepository extends JpaRepository<Payment, Long>{

}
