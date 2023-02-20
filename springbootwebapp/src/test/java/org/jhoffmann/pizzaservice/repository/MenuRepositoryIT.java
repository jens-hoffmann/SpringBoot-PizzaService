package org.jhoffmann.pizzaservice.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.jhoffmann.pizzaservice.domain.Dish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class MenuRepositoryIT {

    @Autowired
    private MenuRepository menuRepository;

    @Test
    public void findAllGivesNonEmptyList() {
        final List<Dish> menu = menuRepository.findAll();
        assertThat(menu.size()).isGreaterThan(0);
    }

    @Test
    public void findAllGivesNonNullDishTypes() {
        final List<Dish> menu = menuRepository.findAll();
        assertThat(menu).extracting(Dish::getName).isNotEmpty();
        assertThat(menu).extracting(Dish::getPrice).allMatch(price -> price > 0.0F);
    }

}
