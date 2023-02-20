package org.jhoffmann.pizzaservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Slf4j
@Controller
@RequestMapping("/purchasereport")
@SessionAttributes("orderObject")
public class PurchaseReportController {

    @GetMapping
    public String showPurchaseReport(SessionStatus sessionStatus) {
        log.info("GET showPurchaseReport");
        if (sessionStatus.isComplete()) {
            return "redirect:/";
        }
        sessionStatus.setComplete();
        return "purchase_report";
    }
}
