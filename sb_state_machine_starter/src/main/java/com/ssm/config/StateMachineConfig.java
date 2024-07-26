package com.ssm.config;

import java.util.EnumSet;
import java.util.Random;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import com.ssm.enumconstants.PaymentEvent;
import com.ssm.enumconstants.PaymentState;
import com.ssm.service.PaymentServiceIml;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Naveen K Wodeyar
 * @date 25-Jul-2024
 */
@Configuration
@Slf4j
@EnableStateMachineFactory
public class StateMachineConfig extends StateMachineConfigurerAdapter<PaymentState, PaymentEvent> {

	@Override
	public void configure(StateMachineStateConfigurer<PaymentState, PaymentEvent> states) throws Exception {
		states.withStates().initial(PaymentState.NEW).states(EnumSet.allOf(PaymentState.class)).end(PaymentState.AUTH)
				.end(PaymentState.PRE_AUTH_ERROR).end(PaymentState.AUTH_ERROR);

	}

	@Override
	public void configure(StateMachineTransitionConfigurer<PaymentState, PaymentEvent> transitions) throws Exception {
		transitions.withExternal().source(PaymentState.NEW).target(PaymentState.NEW).event(PaymentEvent.AUTHORIZE).action(preAuthAction()).and()
				.withExternal().source(PaymentState.NEW).target(PaymentState.PRE_AUTH)
				.event(PaymentEvent.PRE_AUTH_APPROVED).and().withExternal().source(PaymentState.NEW)
				.target(PaymentState.PRE_AUTH_ERROR).event(PaymentEvent.PRE_AUTH_DECLINED);
	}

	@Override
	public void configure(StateMachineConfigurationConfigurer<PaymentState, PaymentEvent> config) throws Exception {
		StateMachineListenerAdapter<PaymentState, PaymentEvent> stateMachineListenerAdapter = new StateMachineListenerAdapter<>() {

			@Override
			public void stateChanged(State<PaymentState, PaymentEvent> from, State<PaymentState, PaymentEvent> to) {
				super.stateChanged(from, to);
				log.info(String.format("stateChanged(from: %s, to %s)", from, to));
			}
		};
		config.withConfiguration().listener(stateMachineListenerAdapter);
	}
	
	public Action<PaymentState, PaymentEvent> preAuthAction(){
		return context->{
			System.out.println("PreAuth called");
			
			if(new Random().nextInt(10)<8) {
				System.out.println("Approved");
				context.getStateMachine().sendEvent(MessageBuilder.withPayload(PaymentEvent.PRE_AUTH_APPROVED)
											.setHeader(PaymentServiceIml.PAYMNET_ID_HEADER, context.getMessageHeader(PaymentServiceIml.PAYMNET_ID_HEADER))
											.build());
			}else {
				System.out.println("Declined");
				context.getStateMachine().sendEvent(MessageBuilder.withPayload(PaymentEvent.PRE_AUTH_DECLINED)
						.setHeader(PaymentServiceIml.PAYMNET_ID_HEADER, context.getMessageHeader(PaymentServiceIml.PAYMNET_ID_HEADER))
						.build());
			}
		};
	}

}
