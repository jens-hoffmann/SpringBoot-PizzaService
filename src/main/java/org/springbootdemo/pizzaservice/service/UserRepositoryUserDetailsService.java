package org.springbootdemo.pizzaservice.service;

import org.springbootdemo.pizzaservice.domain.StaffUser;
import org.springbootdemo.pizzaservice.repository.StaffUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserRepositoryUserDetailsService implements UserDetailsService {

    private final StaffUserRepository staffUserRepository;

    public UserRepositoryUserDetailsService(StaffUserRepository staffUserRepository) {
        this.staffUserRepository = staffUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        StaffUser staffUser = staffUserRepository.findByUsername(username);
        if (staffUser != null) {
            return staffUser;
        } else {
            throw new UsernameNotFoundException( "User '" + username + "' not found");
        }
    }
}
