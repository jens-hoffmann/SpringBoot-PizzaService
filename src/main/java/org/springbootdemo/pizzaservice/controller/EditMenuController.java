package org.springbootdemo.pizzaservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springbootdemo.pizzaservice.domain.Dish;
import org.springbootdemo.pizzaservice.domain.OrderItem;
import org.springbootdemo.pizzaservice.repository.MenuRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/editmenu" )
@SessionAttributes("menuList")
public class EditMenuController {
    private final MenuRepository menuRepository;

    public EditMenuController(MenuRepository menuRepository) {
        log.info("Init EditMenuController");
        this.menuRepository = menuRepository;
    }
    @ModelAttribute( name = "dish")
    public Dish makeNewOrderItem() {
        Dish dish = new Dish();
        log.info("new dish " + dish);
        return dish;
    }

    @GetMapping
    public String getMenuCardForEdit(final Model model) {
        log.info("EditMenuController: Create menu edit view");
        model.addAttribute("menuList", menuRepository.findAll());
        return "editmenu";
    }

    @PostMapping("/menu-item")
    public String addMenuItem(@ModelAttribute("dish")  @Valid Dish dish, Errors errors) {
        log.info("POST addMenuItem: on " + dish);

        if (errors.hasErrors()) {
            return "editmenu";
        }
        menuRepository.save(dish);
        return "redirect:/editmenu";
    }


    @DeleteMapping("/menu-item/{businesskey}")
    public String deleteMenuItem(@PathVariable String businesskey) {
        log.info("DELETE deleteMenuItem: " + businesskey);
        Optional<Dish> dishForDishKey = menuRepository.getDishForDishKey(businesskey);
        if (dishForDishKey.isPresent()) {
            log.info("Delete dish " + dishForDishKey.get().getName());
            menuRepository.delete(dishForDishKey.get());
        }
        return "redirect:/editmenu";
    }

}
