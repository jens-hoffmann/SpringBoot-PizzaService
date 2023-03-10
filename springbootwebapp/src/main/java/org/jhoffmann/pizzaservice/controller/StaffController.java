package org.jhoffmann.pizzaservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.jhoffmann.pizzaservice.domain.StaffUser;
import org.jhoffmann.pizzaservice.repository.StaffUserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    @PreAuthorize("hasRole('STAFF')")
    public String staffLanding(Model model, SessionStatus sessionStatus, @AuthenticationPrincipal StaffUser staffUser) {
        log.info("GET staffLanding: " + staffUser.getFullname());
        model.addAttribute("staffuserlongname", staffUser.getFullname());

        return "staff";
    }
}
