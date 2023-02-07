package org.springbootdemo.pizzaservice.security;

import lombok.Data;
import org.springbootdemo.pizzaservice.domain.StaffUser;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotEmpty;

@Data
public class RegistrationForm {

    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    @NotEmpty
    private String fullname;

    public StaffUser toUser(PasswordEncoder passwordEncoder) {
        return new StaffUser(
                username, passwordEncoder.encode(password),
                fullname);
    }

}
