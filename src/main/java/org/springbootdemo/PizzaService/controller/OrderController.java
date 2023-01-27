package org.springbootdemo.PizzaService.controller;

import java.net.http.HttpRequest;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.springbootdemo.PizzaService.domain.Dish;
import org.springbootdemo.PizzaService.repository.MenuRepository;
import org.springbootdemo.PizzaService.repository.OrderRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import org.springbootdemo.PizzaService.domain.DishOrder;
import org.springbootdemo.PizzaService.domain.OrderItem;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.ui.Model;

@Slf4j
@Controller
@SessionAttributes("orderObject")
public class OrderController {
    private final MenuRepository menuRepository;
    private final OrderRepository orderRepository;
    private final ObjectFactory<ShoppingCart> shoppingCart;
    
    public OrderController(MenuRepository menuRepository, OrderRepository orderRepository, ObjectFactory<ShoppingCart> shoppingCart) {
        log.info("Init OrderController");
        this.menuRepository = menuRepository;
        this.orderRepository = orderRepository;
        this.shoppingCart = shoppingCart;
    }

    @ModelAttribute( name = "menuList")
    public List<Dish> getMenu() {
        return menuRepository.getMenu();
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
        log.info("Create order view");
        return "order";
    }
    

    @GetMapping ("/checkout")
    public String getCheckoutView(@ModelAttribute DishOrder order) {
        log.info("Create checkout view");
        
        ShoppingCart cart = this.shoppingCart.getObject();
        
        if (cart.getContent().isEmpty()) {
            return "redirect:/order";            
        } else {
            order.setShoppingCart(cart);
            return "checkout";            
        }    
    }
    

    @PostMapping("/order/{id}")
    public String putOrder(@PathVariable String id, @ModelAttribute OrderItem orderItem ) {
        
        log.info("putOrder: " + id + " on " + orderItem);
        this.shoppingCart.getObject()
                .add(orderItem);
        
        return "redirect:/order";
    }

    @PostMapping("/checkout")
    public String checkoutOrder() {
                
        log.info("checkoutOrder");
        ShoppingCart cart = this.shoppingCart.getObject();
        if (cart.getContent().isEmpty()) {
            log.warn("Try to checkout empty shopping cart");
            return "redirect:/order";       
        } else {
            return "checkout";
        }
        
    }
    
    @RequestMapping(value="/order/{name}", method = RequestMethod.DELETE)
    public String removeFromCart(
            @PathVariable String name
    ) {
        this.shoppingCart.getObject()
                .removeByName(name);
        return "redirect:/order";
    }
    
}
