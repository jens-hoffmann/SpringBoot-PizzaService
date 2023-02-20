package org.jhoffmann.pizzaservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.jhoffmann.pizzaservice.domain.DishOrder;
import org.jhoffmann.pizzaservice.domain.Payment;
import org.jhoffmann.pizzaservice.repository.OrderRepository;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/checkout")
@SessionAttributes("orderObject")
public class CheckoutController {
    private final OrderRepository orderRepository;

    private final ObjectFactory<Payment> payment;

    public CheckoutController(ObjectFactory<Payment> payment,
                              OrderRepository orderRepository) {
        this.payment = payment;
        this.orderRepository = orderRepository;
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
            return "redirect:/purchasereport";
        }

    }
}
