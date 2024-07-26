package com.ssm.service;

import java.util.Optional;

import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

import com.ssm.entity.Payment;
import com.ssm.enumconstants.PaymentEvent;
import com.ssm.enumconstants.PaymentState;
import com.ssm.repo.PaymentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Naveen K Wodeyar
 * @date 26-Jul-2024
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class PaymentStateListner extends StateMachineInterceptorAdapter<PaymentState, PaymentEvent> {
	
	private final PaymentRepository paymentRepository;

	@Override
	public void preStateChange(State<PaymentState, PaymentEvent> state, Message<PaymentEvent> message,
			Transition<PaymentState, PaymentEvent> transition, StateMachine<PaymentState, PaymentEvent> stateMachine,
			StateMachine<PaymentState, PaymentEvent> rootStateMachine) {
		Optional.ofNullable(message).ifPresent(msg->{
			Optional.ofNullable(Long.class.cast(msg.getHeaders().getOrDefault(PaymentServiceIml.PAYMNET_ID_HEADER, -1L)))
			.ifPresent(pId->{
				Payment payment = paymentRepository.getOne(pId);
				payment.setState(state.getId());
				paymentRepository.save(payment);
				
			});
		});
	}
	
	

	
}
