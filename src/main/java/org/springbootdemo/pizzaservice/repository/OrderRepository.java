package org.springbootdemo.pizzaservice.repository;

import org.springbootdemo.pizzaservice.domain.Dish;
import org.springbootdemo.pizzaservice.domain.DishOrder;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<DishOrder, Long> {

}
