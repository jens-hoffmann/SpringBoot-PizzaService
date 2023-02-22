package org.jhoffmann.pizzaservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.jhoffmann.pizzaservice.domain.DishOrder;
import org.jhoffmann.pizzaservice.domain.OrderItem;
import org.jhoffmann.pizzaservice.domain.OrderItemPOJO;
import org.jhoffmann.pizzaservice.repository.OrderRepository;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class JmsReceiverService {

    private final OrderRepository orderRepository;

    public JmsReceiverService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @JmsListener(destination = "${jms.orderitem.kitchen_outgoing_queue}", containerFactory = "myFactory")
    public void receiveOrderItemFromKitchen(String message) {

        try {
            final OrderItemPOJO orderItemPOJO = new ObjectMapper().readValue(message, OrderItemPOJO.class);
            log.info("JmsReceiverService: Received OrderItem through JMS: " + orderItemPOJO);
            Optional<DishOrder> firstDishOrder = orderRepository.findByBusinesskey(orderItemPOJO.getOrderId()).stream().findFirst();
            if (firstDishOrder.isPresent()) {
                Optional<OrderItem> firstOrderItem = firstDishOrder.get().getDishesOrder().stream().filter(item -> item.getBusinesskey().equals(orderItemPOJO.getBusinesskey())).findFirst();
                if (firstOrderItem.isPresent()) {
                    OrderItem orderItem = firstOrderItem.get();
                    orderItem.setDishStatus(OrderItem.DishStatus.COOKED);
                    log.info("JmsReceiverService: OrderItem from repository: " + orderItem);

                }

            }
        } catch (JsonProcessingException ex) {
            log.error("JmsReceiverService: Exception on deserialization", ex);
            throw new RuntimeException(ex); // TODO add custom exception with error handling
        }

    }
}
