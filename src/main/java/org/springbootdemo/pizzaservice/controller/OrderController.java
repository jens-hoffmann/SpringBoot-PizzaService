package org.springbootdemo.pizzaservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springbootdemo.pizzaservice.domain.Dish;
import org.springbootdemo.pizzaservice.repository.MenuRepository;
import org.springbootdemo.pizzaservice.repository.OrderRepository;
import org.springbootdemo.pizzaservice.service.ShoppingCart;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springbootdemo.pizzaservice.domain.DishOrder;
import org.springbootdemo.pizzaservice.domain.OrderItem;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.validation.Errors;

@Slf4j
@Controller
@SessionAttributes("orderObject")
public class OrderController {
    private final MenuRepository menuRepository;
    private final ObjectFactory<ShoppingCart> shoppingCart;
    
    public OrderController(MenuRepository menuRepository, ObjectFactory<ShoppingCart> shoppingCart) {
        log.info("Init OrderController");
        this.menuRepository = menuRepository;
        this.shoppingCart = shoppingCart;
    }

    @ModelAttribute( name = "menuList")
    public List<Dish> getMenu() {
        return menuRepository.findAll();
    }

    @ModelAttribute( name = "orderObject")
    public DishOrder makeNewOrder() {
        
        DishOrder order = new DishOrder();
        log.info("new orderObject " + order.getSessionId());
        return order;
    }
    
    @ModelAttribute( name = "orderItem")
    public OrderItem makeNewOrderItem() {
        OrderItem orderItem = new OrderItem();
        log.info("new orderItem " + orderItem);
        return orderItem;
    }
    
    @GetMapping ("/order")
    public String getOrderView() {
        log.info("GET Create order view");
        return "order";
    }

    @PostMapping("/order-item")
    public String putOrder(@ModelAttribute("orderItem") @Valid OrderItem orderItem , Errors errors) {
        
        log.info("POST putOrder: on " + orderItem);
        
        if (errors.hasErrors()) {
            
            return "order";
        }
        this.shoppingCart.getObject()
                .add(orderItem);
        
        return "redirect:/order";
    }


    @RequestMapping(value="/order-item/{name}", method = RequestMethod.DELETE)
    public String removeFromCart(@PathVariable String name) {
        log.info("DELETE removeFromCart");
        this.shoppingCart.getObject()
                .removeByName(name);
        return "redirect:/order";
    }

    @PostMapping("/order")
    public String processPizza(
            @ModelAttribute("orderObject") DishOrder orderObject, Errors errors, HttpServletRequest request) {
        log.info("Processing pizza: {}", orderObject);

        if (errors.hasErrors()) {
            return "order";
        }

        orderObject.setDishesOrder(this.shoppingCart.getObject().getContent());
        orderObject.setTotalPrice(this.shoppingCart.getObject().getTotalPrice());
        orderObject.setIpAdress(request.getRemoteAddr().toString());

        log.info("After Processing pizza: {}", orderObject);

        return "redirect:/checkout";
    }
    
}
