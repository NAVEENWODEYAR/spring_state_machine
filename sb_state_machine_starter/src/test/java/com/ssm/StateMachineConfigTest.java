package com.ssm;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;

import com.ssm.enumconstants.PaymentEvent;
import com.ssm.enumconstants.PaymentState;

/**
 * @author Naveen K Wodeyar
 * @date 26-Jul-2024
 */
@SpringBootTest
public class StateMachineConfigTest {
	
	@Autowired	
	StateMachineFactory<PaymentState, PaymentEvent> stateMachineFactory;
	
	@Test
	void testNewState() {
		StateMachine<PaymentState, PaymentEvent> st = stateMachineFactory.getStateMachine(UUID.randomUUID());
		st.start();
		System.out.println(st.getState().toString());
		st.sendEvent(PaymentEvent.PRE_AUTHORIZE);
		System.out.println(st.getState().toString());
		st.sendEvent(PaymentEvent.PRE_AUTH_APPROVED);
		System.out.println(st.getState().toString());
		st.sendEvent(PaymentEvent.PRE_AUTH_APPROVED);
		System.out.println(st.getState().toString());
		st.sendEvent(PaymentEvent.PRE_AUTH_DECLINED);
		System.out.println(st.getState().toString());
		
		
		
	}

}
