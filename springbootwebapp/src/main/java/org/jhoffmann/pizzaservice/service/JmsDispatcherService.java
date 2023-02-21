package org.jhoffmann.pizzaservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.jhoffmann.pizzaservice.domain.OrderItemPOJO;
import org.jhoffmann.pizzaservice.domain.OrderItem;
import org.jhoffmann.pizzaservice.domain.OrderItemPOJO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class JmsDispatcherService {


    private final JmsTemplate jmsTemplate;

    @Value("${jms.orderitem.kitchen_incoming_queue}")
    private String jmsQueue;

    public JmsDispatcherService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendOrderItemToKitchen(OrderItemPOJO orderItem) {
        log.debug("JmsDispatcherService: sendOrderItem:" + orderItem);
        String serializedString = null;
        try {
            serializedString = new ObjectMapper().writeValueAsString(orderItem);
            jmsTemplate.convertAndSend(jmsQueue, serializedString);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e); // TODO add custom exception with error handling
        }

    }
}
