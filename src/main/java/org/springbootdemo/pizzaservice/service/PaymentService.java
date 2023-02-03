package org.springbootdemo.pizzaservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springbootdemo.pizzaservice.domain.Payment;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Slf4j
@Component
@SessionScope
public class PaymentService {

    public Payment payment;

    public PaymentService(Payment payment) {
        this.payment = payment;
    }
}
