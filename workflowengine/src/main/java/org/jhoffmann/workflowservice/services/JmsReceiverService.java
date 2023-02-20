package org.jhoffmann.workflowservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.EventSubscription;
import org.camunda.bpm.engine.runtime.MessageCorrelationResult;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.jhoffmann.workflowservice.domain.OrderItemPOJO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            log.info("JmsReceiverService: Received OrderItem through JMS: "+ orderItemPOJO);

            Map<String, Object> processVariables = new HashMap<>();
            String businesskey = orderItemPOJO.getBusinesskey();
            processVariables.put("dishname", orderItemPOJO.getDishname());
            processVariables.put("amount", orderItemPOJO.getAmount());

            //ProcessInstance pi = runtimeService.startProcessInstanceByMessage("newIncomingOrderMessage");
            ProcessInstance pi = runtimeService.startProcessInstanceByMessage("newIncomingOrderMessage", businesskey, processVariables);
            String processInstanceId = pi.getProcessInstanceId();
            log.info("JmsReceiverService: Started process instance " + processInstanceId);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e); // TODO add custom exception with error handling
        }

    }
}
