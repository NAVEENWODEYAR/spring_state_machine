package com.ssm;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;

import com.ssm.entity.Payment;
import com.ssm.enumconstants.PaymentEvent;
import com.ssm.enumconstants.PaymentState;
import com.ssm.repo.PaymentRepository;
import com.ssm.service.PaymentService;

import jakarta.transaction.Transactional;

/**
 * @author Naveen K Wodeyar
 * @date 26-Jul-2024
 */
@SpringBootTest
public class PaymentServiceImlTest {
	
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	PaymentRepository paymentRepository;
	
	Payment payment;
	
	
	@BeforeEach
	void setUp() {
		payment = Payment.builder().amount(new BigDecimal("12.99")).build();
	}
	
	@Transactional
	@Test
	void preAuth() {
		Payment newPayment = paymentService.newPayment(payment);
		StateMachine<PaymentState, PaymentEvent> preAuth = paymentService.preAuth(newPayment.getPId());
		Payment prePayment =paymentRepository.findById(newPayment.getPId()).get();
		System.out.println(preAuth.getState().getId());
		System.out.println(prePayment);
	}

}
