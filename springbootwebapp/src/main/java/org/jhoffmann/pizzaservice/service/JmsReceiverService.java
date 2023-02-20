package org.jhoffmann.pizzaservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class JmsReceiverService {

    @JmsListener(destination = "${pizzaservice.jms.kitchen_outgoing_queue}", containerFactory = "myFactory")
    public void receiveOrderItem(String message) {
        log.info("JmsReceiverService: message from kitchen_outgoing_queue : "  + message);
    }
}
