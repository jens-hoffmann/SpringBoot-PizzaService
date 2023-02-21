package org.jhoffmann.workflowservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class JmsDispatcherService {


    private final JmsTemplate jmsTemplate;

    @Value("${jms.orderitem.kitchen_outgoing_queue}")
    private String jmsQueue;

    public JmsDispatcherService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendOrderItemToWebApp(String message) {
        jmsTemplate.convertAndSend(jmsQueue, message);
    }

}
