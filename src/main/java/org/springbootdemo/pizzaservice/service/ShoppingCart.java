package org.springbootdemo.pizzaservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springbootdemo.pizzaservice.domain.Dish;
import org.springbootdemo.pizzaservice.domain.OrderItem;
import org.springbootdemo.pizzaservice.repository.MenuRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@SessionScope
public class ShoppingCart {

    private final MenuRepository menuRepository;

    private final List<OrderItem> orderItems = new ArrayList<>();
    private float totalPrice = 0.0F;

    public ShoppingCart(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;

    }

    public void add(final OrderItem orderItem) {
        Optional<OrderItem> first = this.orderItems.stream().filter(orItem -> orItem.getDishkey().equals(orderItem.getDishkey())).findFirst();
        if (first.isPresent()) {
            OrderItem it = first.get();
            it.setAmount(it.getAmount() + orderItem.getAmount());
        } else {
            this.orderItems.add(orderItem);
        }
        recalcTotalPrice();

    }

    public void removeByName(final String dish) {
        this.orderItems
                .removeIf(orderItem -> orderItem.getDishkey().equals(dish));
        recalcTotalPrice();
    }

    public List<OrderItem> getContent() {
        return Collections
                .unmodifiableList(orderItems);
    }

    public float getTotalPrice() {
        return this.totalPrice;
    }

    public void recalcTotalPrice() {
        totalPrice = 0.0F;
        for (OrderItem item : orderItems) {
            log.info("recalcTotalPrice: found dish " + item.getDishkey());
            Optional<Dish> dishForDishName = menuRepository.getDishForDishKey(item.getDishkey());
            if (dishForDishName.isPresent()) {
                totalPrice += dishForDishName.get().getPrice() * item.getAmount();
            }

        }
        log.info("recalcTotalPrice: new price is " + totalPrice);
    }
}