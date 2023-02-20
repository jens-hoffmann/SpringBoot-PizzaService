package org.jhoffmann.pizzaservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
//@EnableWebSecurity(debug=true)
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests()
                    .antMatchers("/staff", "/editmenu").hasRole("STAFF")
                    .antMatchers("/", "/**", "/h2-console/**").permitAll()
                    .requestMatchers(EndpointRequest.to("health", "info")).permitAll()
                    //.requestMatchers(EndpointRequest.toAnyEndpoint()).hasRole("ENDPOINT_ADMIN")
                    .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                .and()
                    .formLogin()
                    .loginPage("/login")

                .and()
                    .logout()
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")

                // Make H2-Console non-secured; for debug purposes
                .and()
                    .csrf()
                    .ignoringAntMatchers("/h2-console/**")

                .and()
                    .build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}