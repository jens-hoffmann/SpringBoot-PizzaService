package org.jhoffmann.pizzaservice.repository;

import org.jhoffmann.pizzaservice.domain.StaffUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StaffUserRepository  extends JpaRepository<StaffUser, Long> {

    @Query
    StaffUser findByUsername(String username);
}
