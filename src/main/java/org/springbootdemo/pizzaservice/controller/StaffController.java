package org.springbootdemo.pizzaservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springbootdemo.pizzaservice.domain.StaffUser;
import org.springbootdemo.pizzaservice.repository.StaffUserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("/staff")
public class StaffController {
    private final StaffUserRepository staffUserRepository;

    public StaffController(StaffUserRepository staffUserRepository) {
        this.staffUserRepository = staffUserRepository;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public String staffLanding(Model model, SessionStatus sessionStatus, Principal principal) {
        String staffuserlongname = staffUserRepository.findByUsername(principal.getName()).getFullname();
        log.info("GET staffLanding: " + staffuserlongname);
        model.addAttribute("staffuserlongname", staffuserlongname);
        return "staff";
    }
}
