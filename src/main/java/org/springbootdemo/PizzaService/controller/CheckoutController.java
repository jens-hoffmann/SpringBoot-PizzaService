package org.springbootdemo.PizzaService.controller;

import lombok.extern.slf4j.Slf4j;
import org.springbootdemo.PizzaService.domain.DishOrder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/checkout")
@SessionAttributes("orderObject")
public class CheckoutController {
/*
    @GetMapping("/current")
    public String checkout() {
        return "checkout";
    }
*/
    @GetMapping
    public String getCheckoutView(@ModelAttribute("orderObject") DishOrder order) {
        log.info("GET Create checkout view");
        if (order.getDishesOrder().isEmpty()) {
            return "redirect:/order";
        } else {
            //order.setShoppingCart(cart);
            return "checkout";
        }
    }

    @PostMapping
    public String checkoutOrder(@ModelAttribute("orderObject") @Valid DishOrder orderObject , Errors errors, SessionStatus sessionStatus) {

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
            sessionStatus.setComplete();
            return "purchase_report";
        }

    }
}
