package org.springbootdemo.PizzaService.controller;

import org.springbootdemo.PizzaService.domain.OrderItem;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@SessionScope
public class ShoppingCart {
	private final List<OrderItem> orderItems = new ArrayList<>();
	
	public void add(final OrderItem orderItem) {
		Optional<OrderItem> first = this.orderItems.stream().filter(orItem -> orItem.getDish().equals(orderItem.getDish())).findFirst();
		if (first.isPresent()){
			OrderItem it = first.get();
			it.setAmount(it.getAmount()+orderItem.getAmount());
		}else{
			this.orderItems.add(orderItem);
		}

	}

	public void removeByName(final String dish) {
		this.orderItems
				.removeIf(orderItem -> orderItem.getDish().equals(dish));
	}

	public List<OrderItem> getContent() {
		return Collections
			.unmodifiableList(orderItems);
	}
}