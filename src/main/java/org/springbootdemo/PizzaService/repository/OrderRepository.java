package org.springbootdemo.PizzaService.repository;

import org.springbootdemo.PizzaService.domain.DishOrder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepository {
    private List<DishOrder> orderList;

    public OrderRepository() {
        orderList = new ArrayList<>();
    }

    public void addOrder(DishOrder order) {
        orderList.add(order);
    }

    public List<DishOrder> getOrderList() {
        return orderList;
    }
}
