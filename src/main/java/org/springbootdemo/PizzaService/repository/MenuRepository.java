/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.springbootdemo.PizzaService.repository;

import org.springbootdemo.PizzaService.domain.Menu;
import org.springbootdemo.PizzaService.domain.Dish;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;


/**
 *
 * @author hoffi
 */
@Slf4j
@Repository
public class MenuRepository {
    
    private Menu menu;

    public MenuRepository() {
        log.info("Init MenuRepository");
        List<Dish> dishList = List.of(
                new Dish("Spagetthi", 5.9F),
                new Dish("Pizza Napoli", 6.9F),
                new Dish("Pizza Frutti di Mare", 7.9F));
        menu = new Menu(dishList);
    }

    public List<Dish> getMenu() {
        return this.menu.getDishList();
    }
    
    
}
