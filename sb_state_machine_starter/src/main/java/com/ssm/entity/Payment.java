package com.ssm.entity;

import java.math.BigDecimal;
import com.ssm.domain.PaymentState;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Naveen K Wodeyar
 * @date 25-Jul-2024
 */
@Entity
@Table(name = "PAYMENT")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long pId;
	
	@Enumerated(EnumType.STRING)
	private PaymentState state;
	
	private BigDecimal amount;
}

