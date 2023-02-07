package org.springbootdemo.pizzaservice.controller;

import org.springbootdemo.pizzaservice.repository.StaffUserRepository;
import org.springbootdemo.pizzaservice.security.RegistrationForm;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private StaffUserRepository staffUserRepository;
    private PasswordEncoder passwordEncoder;

    public RegistrationController(
            StaffUserRepository staffUserRepository, PasswordEncoder passwordEncoder) {
        this.staffUserRepository = staffUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String registerForm() {
        return "registration";
    }

    @PostMapping
    public String processRegistration(@Valid RegistrationForm form) {
        staffUserRepository.save(form.toUser(passwordEncoder));
        return "redirect:/login";
    }

}
