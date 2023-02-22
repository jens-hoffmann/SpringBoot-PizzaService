package org.jhoffmann.pizzaservice.repository;

import org.jhoffmann.pizzaservice.domain.Dish;
import org.jhoffmann.pizzaservice.domain.DishOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OrderRepository extends JpaRepository<DishOrder, Long> {
    List<DishOrder> findByBusinesskey(String businesskey);

}
