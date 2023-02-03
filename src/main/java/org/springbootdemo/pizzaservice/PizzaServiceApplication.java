package org.springbootdemo.pizzaservice;

import org.springbootdemo.pizzaservice.domain.Dish;
import org.springbootdemo.pizzaservice.repository.MenuRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class PizzaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PizzaServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner dataLoader(MenuRepository menuRepository) {
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
				List<Dish> dishList = List.of(
						new Dish("Spagetthi", 5.9F),
						new Dish("Pizza Napoli", 6.9F),
						new Dish("Pizza Frutti di Mare", 7.9F));
				menuRepository.saveAllAndFlush(dishList);
			}
		};
	}

}
