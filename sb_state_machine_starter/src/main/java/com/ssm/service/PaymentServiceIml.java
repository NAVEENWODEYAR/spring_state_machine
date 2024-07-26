package com.ssm.service;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
/**
 * @author Naveen K Wodeyar
 * @date 26-Jul-2024
 */
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;

import com.ssm.entity.Payment;
import com.ssm.enumconstants.PaymentEvent;
import com.ssm.enumconstants.PaymentState;
import com.ssm.repo.PaymentRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class PaymentServiceIml implements PaymentService {

	public static final String PAYMNET_ID_HEADER = "paymentId";
	
	private final PaymentRepository paymentRepository;
	
	private final StateMachineFactory<PaymentState, PaymentEvent> stateMachineFactory;
	
	private final PaymentStateListner paymentStateListner;
	
	
	@Override
	public Payment newPayment(Payment payment) {
		payment.setState(PaymentState.NEW);
		return paymentRepository.save(payment);
	}

	@Transactional
	@Override
	public StateMachine<PaymentState, PaymentEvent> preAuth(Long pId) {
		StateMachine<PaymentState, PaymentEvent> build = build(pId);
		sendEvent(pId, build, PaymentEvent.PRE_AUTHORIZE);
		return build;
	}

	@Transactional
	@Override
	public StateMachine<PaymentState, PaymentEvent> authorizePayment(Long pId) {
		StateMachine<PaymentState, PaymentEvent> build = build(pId);
		sendEvent(pId, build, PaymentEvent.PRE_AUTH_APPROVED);
		return build;
	}

	@Transactional
	@Override
	public StateMachine<PaymentState, PaymentEvent> declineAuth(Long pId) {
		StateMachine<PaymentState, PaymentEvent> build = build(pId);
		sendEvent(pId, build, PaymentEvent.PRE_AUTH_DECLINED);
		return build;
	}

	private void sendEvent(Long pId, StateMachine<PaymentState, PaymentEvent> sm,PaymentEvent event) {
		Message message = MessageBuilder.withPayload(event)
							.setHeader(PAYMNET_ID_HEADER, pId)
							.build();
				sm.sendEvent(message);
	}
	
	private StateMachine<PaymentState, PaymentEvent> build(Long pId){
		Payment payment = paymentRepository.findById(pId).get();
		
		StateMachine<PaymentState, PaymentEvent> stateMachine = stateMachineFactory.getStateMachine(Long.toString(pId));
		stateMachine.stop();
		
		stateMachine.getStateMachineAccessor()
					.doWithAllRegions(t ->{
						t.addStateMachineInterceptor(paymentStateListner);
						t.resetStateMachine(new DefaultStateMachineContext<PaymentState, PaymentEvent>(null, null, null, null));
					});
		stateMachine.start();
		
		return stateMachine;
	}
}
