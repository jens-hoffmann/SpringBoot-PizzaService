package org.jhoffmann.workflowservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.EventSubscription;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.jhoffmann.workflowservice.domain.OrderItemPOJO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class JmsReceiverService {


    RuntimeService runtimeService;

    public JmsReceiverService(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    @JmsListener(destination = "${pizzaservice.jms.kitchen_incoming_queue}", containerFactory = "myFactory")
    public void receiveOrderItem(String message) {
        OrderItemPOJO orderItemPOJO= null;
        try {
            orderItemPOJO = new ObjectMapper().readValue(message, OrderItemPOJO.class);
            log.info("Received OrderItem through JMS: "+ orderItemPOJO);
            ProcessInstance pi = runtimeService.startProcessInstanceByKey("IncomingOrder");

            EventSubscription subscription = runtimeService.createEventSubscriptionQuery()
                    .processInstanceId(pi.getId()).eventType("message").singleResult();

            runtimeService.messageEventReceived(subscription.getEventName(), subscription.getExecutionId());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e); // TODO add custom exception with error handling
        }

    }
}
