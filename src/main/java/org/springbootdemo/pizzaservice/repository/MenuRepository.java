/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.springbootdemo.pizzaservice.repository;

import org.springbootdemo.pizzaservice.domain.Dish;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;



public interface MenuRepository extends JpaRepository<Dish, Long> {
    @Query("SELECT d FROM Dish d WHERE d.businesskey = :dishkey")
    Optional<Dish> getDishForDishKey(String dishkey);


}
