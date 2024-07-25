package com.ssm.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;

import com.ssm.domain.PaymentEvent;
import com.ssm.domain.PaymentState;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Naveen K Wodeyar
 * @date 25-Jul-2024
 */
@Configuration
@Slf4j
@EnableStateMachineFactory
public class StateMachineConfig extends StateMachineConfigurerAdapter<PaymentState, PaymentEvent>{
	
	@Override
	public void configure(StateMachineStateConfigurer<PaymentState, PaymentEvent> states) throws Exception {
		super.configure(states);
	}
	
	
	
	
	

}
