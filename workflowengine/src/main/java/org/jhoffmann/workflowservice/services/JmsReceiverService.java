package org.jhoffmann.workflowservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.EventSubscription;
import org.camunda.bpm.engine.runtime.MessageCorrelationResult;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.camunda.spin.plugin.variable.SpinValues;
import org.camunda.spin.plugin.variable.value.JsonValue;
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

    @JmsListener(destination = "${jms.orderitem.kitchen_incoming_queue}", containerFactory = "myFactory")
    public void receiveOrderItem(String message) {
        OrderItemPOJO orderItemPOJO= null;
        try {
            orderItemPOJO = new ObjectMapper().readValue(message, OrderItemPOJO.class);
            log.info("JmsReceiverService: Received OrderItem through JMS: "+ orderItemPOJO);

            String businesskey = orderItemPOJO.getBusinesskey();

            ProcessInstance pi = runtimeService.startProcessInstanceByMessage("newIncomingOrderMessage");
            String serializedString = new ObjectMapper().writeValueAsString(orderItemPOJO);
            JsonValue jsonValue = SpinValues.jsonValue(serializedString).create();
            runtimeService.setVariable(pi.getId(), "orderItem", jsonValue);


            String processInstanceId = pi.getProcessInstanceId();
            log.info("JmsReceiverService: Started process instance " + processInstanceId);

        } catch (JsonProcessingException e) {
            log.error("JmsReceiverService: Exception on serialization and startin workflow job", e);
            throw new RuntimeException(e); // TODO add custom exception with error handling
        }

    }
}
