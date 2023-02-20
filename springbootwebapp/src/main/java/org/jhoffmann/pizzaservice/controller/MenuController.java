package org.jhoffmann.pizzaservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.jhoffmann.pizzaservice.domain.Dish;
import org.jhoffmann.pizzaservice.repository.MenuRepository;
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
        return menuRepository.findAll();
    }


    @GetMapping
    public String getMenuCard() {
        log.info("Create menu view");
        return "menu";
    }
    
}
