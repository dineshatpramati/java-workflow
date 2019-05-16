package com.example.orderservice;

import lombok.extern.java.Log;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Component;

@Log
@Component
public class Runner implements ApplicationRunner {


    private final StateMachineFactory<OrderStates,OrderEvents> factory;

    public Runner(StateMachineFactory<OrderStates, OrderEvents> factory) {
        this.factory = factory;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        StateMachine<OrderStates,OrderEvents> machine = this.factory.getStateMachine("12321");
        machine.start();
        log.info("current state : "+machine.getState().getId().name());
        machine.sendEvent(OrderEvents.PAY);
        log.info("current state : "+machine.getState().getId().name());
        machine.sendEvent(MessageBuilder.withPayload(OrderEvents.FULFILL).setHeader("a","b").build());
        log.info("current state : "+machine.getState().getId().name());

    }
}
