package com.ssm.service;
/**
 * @author Naveen K Wodeyar
 * @date 26-Jul-2024
 */

import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;

import com.ssm.entity.Payment;
import com.ssm.enumconstants.PaymentEvent;
import com.ssm.enumconstants.PaymentState;

@Service
public interface PaymentService {
	
	Payment newPayment(Payment payment);
	
	StateMachine<PaymentState, PaymentEvent> preAuth(Long pId);
	
	StateMachine<PaymentState, PaymentEvent> authorizePayment(Long pId);
	
	StateMachine<PaymentState, PaymentEvent> declineAuth(Long pId);
	
}
