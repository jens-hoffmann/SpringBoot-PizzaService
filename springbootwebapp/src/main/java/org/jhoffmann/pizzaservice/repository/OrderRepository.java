package org.jhoffmann.pizzaservice.repository;

import org.jhoffmann.pizzaservice.domain.Dish;
import org.jhoffmann.pizzaservice.domain.DishOrder;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<DishOrder, Long> {

}
