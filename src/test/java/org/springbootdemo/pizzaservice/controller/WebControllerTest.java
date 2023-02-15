package org.springbootdemo.pizzaservice.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springbootdemo.pizzaservice.domain.Dish;
import org.springbootdemo.pizzaservice.domain.StaffUser;
import org.springbootdemo.pizzaservice.repository.MenuRepository;
import org.springbootdemo.pizzaservice.repository.StaffUserRepository;
import org.springbootdemo.pizzaservice.security.SecurityConfiguration;
import org.springbootdemo.pizzaservice.service.ShoppingCart;
import org.springbootdemo.pizzaservice.service.UserRepositoryUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@Import({SecurityConfiguration.class, UserRepositoryUserDetailsService.class, ShoppingCart.class})
//@ContextConfiguration(classes = SecurityConfiguration.class)
@WebAppConfiguration
@WebMvcTest({MenuController.class, EditMenuController.class, OrderController.class, StaffController.class})
public class WebControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockBean
    MenuRepository menuRepository;

    @MockBean
    StaffUserRepository staffUserRepository;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        given( menuRepository.findAll() ).willReturn(
                List.of(
                        new Dish("TestDish1", 4.5F),
                        new Dish("TestDish2", 6.4F)
                ));

        given( staffUserRepository.findByUsername("testuser") )
                .willReturn(new StaffUser("testuser", passwordEncoder.encode("password"), "Test User"));
    }

    @Test
    @WithAnonymousUser
    public void test_get_menu_card_is_not_empty() throws  Exception {

        mockMvc.perform( get( "/menu"))
                // .andDo( print() )
                .andExpect( status().isOk())
                .andExpect(view().name("menu"));

    }

    @Test
    @WithAnonymousUser
    public void test_staffsite_anonymous_gives_redirect() throws Exception {
        mockMvc.perform( get( "/staff") )
                // .andDo( print() )
                .andExpect( status().is3xxRedirection())
                .andExpect( redirectedUrlPattern("**/login"));
    }

    @Test
    public void test_staffsite_with_staff_login_role_sucess() throws Exception {
        mockMvc.perform( get( "/staff").with(user(staffUserRepository.findByUsername("testuser"))) )
                // .andDo( print() )
                .andExpect( status().isOk());
    }


    @Test
    @WithAnonymousUser
    public void test_edit_menu_anonymous_gives_redirect() throws Exception {
        mockMvc.perform( get( "/editmenu"))
                // .andDo( print() )
                .andExpect( status().is3xxRedirection())
                .andExpect( redirectedUrlPattern("**/login"));
    }


    @Test
    @WithMockUser(roles="STAFF")
    public void test_edit_menu_with_staff_login_role_sucess() throws Exception {
        mockMvc.perform( get( "/editmenu") )
                // .andDo( print() )
                .andExpect( status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void test_order_items() throws Exception {
        final List<Dish> dishes = menuRepository.findAll();
        assertThat(dishes.size()).isGreaterThan(0);

        mockMvc.perform(MockMvcRequestBuilders.post("/order-item")
                        .param("dishkey", dishes.get(0).getBusinesskey())
                        .param("dishname",dishes.get(0).getName())
                        .param("amount", "3")
                        .with(csrf()))
                .andDo( print() )
                .andExpect(status().is3xxRedirection())
                .andExpect( redirectedUrl("/order"));

    }

}
