/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.springbootdemo.PizzaService.controller;

import org.springbootdemo.PizzaService.repository.MenuRepository;
import org.springbootdemo.PizzaService.domain.Dish;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import org.springframework.web.bind.annotation.SessionAttributes;

@Slf4j
@Controller
@RequestMapping("/menu" )
@SessionAttributes("menuList")
public class MenuController {

    private final MenuRepository menuRepository;

    public MenuController(MenuRepository menuRepository) {
        log.info("Init MenuController");
        this.menuRepository = menuRepository;
    }

    @ModelAttribute( name = "menuList")
    public List<Dish> getMenu() {
        return menuRepository.getMenu();
    }

    @GetMapping
    public String getMenuCard() {
        log.info("Create menu view");
        return "menu";
    }
    
}
