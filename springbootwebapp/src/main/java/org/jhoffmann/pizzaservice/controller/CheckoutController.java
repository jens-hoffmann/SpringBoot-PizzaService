package org.jhoffmann.pizzaservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.jhoffmann.pizzaservice.domain.DishOrder;
import org.jhoffmann.pizzaservice.domain.OrderItemPOJO;
import org.jhoffmann.pizzaservice.domain.Payment;
import org.jhoffmann.pizzaservice.repository.OrderRepository;
import org.jhoffmann.pizzaservice.service.JmsDispatcherService;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/checkout")
@SessionAttributes("orderObject")
public class CheckoutController {
    private final OrderRepository orderRepository;

    private final ObjectFactory<Payment> payment;
    private final JmsDispatcherService jmsDispatcherService;
    public CheckoutController(ObjectFactory<Payment> payment,
                              OrderRepository orderRepository, JmsDispatcherService jmsDispatcherService) {
        this.payment = payment;
        this.orderRepository = orderRepository;
        this.jmsDispatcherService = jmsDispatcherService;
    }

    @GetMapping
    public String getCheckoutView(@ModelAttribute("orderObject") DishOrder order) {
        log.info("GET Create checkout view");
        if (order.getDishesOrder().isEmpty()) {
            return "redirect:/order";
        } else {
            return "checkout";
        }
    }

    @PostMapping
    public String checkoutOrder(@ModelAttribute("orderObject") @Valid DishOrder orderObject , Errors errors) {

        log.info("POST checkoutOrder " + orderObject);

        if (errors.hasErrors()) {

            log.error("ERROR: " + errors.getAllErrors().stream().
                    map(e -> e.getObjectName() + ": " + e.getDefaultMessage()).collect(Collectors.toList()));
            return "checkout";
        }
        if (orderObject.getDishesOrder().isEmpty()) {
            log.warn("Try to checkout empty shopping cart");
            return "redirect:/order";
        } else {
            orderRepository.save(orderObject);
            List<OrderItemPOJO> orderitemppojolist = orderObject.getDishesOrder().stream()
                    .map(item -> new OrderItemPOJO(item.getBusinesskey(), item.getDishkey(), item.getDishname(), item.getAmount()))
                    .collect(Collectors.toList());
            orderitemppojolist.forEach(pojo -> {
                try {
                    jmsDispatcherService.sendOrderItem(pojo);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            return "redirect:/purchasereport";
        }

    }
}
