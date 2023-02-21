package ru.itis.controllers;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.itis.dto.*;
import ru.itis.services.OrdersService;
import ru.itis.services.UsersService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(UsersController.class)
@DisplayName("UsersController")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class UsersControllerTest {

    private static final UserDto USER_1 = UserDto.builder()
            .id(UUID.fromString("4896c91b-9e61-3129-87b6-8aa299028111"))
            .build();
    private static final ShopAddressDto SHOP_ADDRESS_1 = ShopAddressDto.builder()
            .address("Moscow")
            .build();
    private static final ProductDto PRODUCT_1 = ProductDto.builder()
            .name("Concealer")
            .build();
    private static final ProductDto PRODUCT_2 = ProductDto.builder()
            .name("Ink")
            .build();

    private static final Set<ProductDto> PRODUCTS_SET = new HashSet<>(Arrays.asList(PRODUCT_1, PRODUCT_2));


    private static final OrderDto ORDER_1 = OrderDto.builder()
            .id(UUID.fromString("4896c91b-9e61-3129-87b6-8aa299028058"))
            .user(USER_1)
            .shopAddress(SHOP_ADDRESS_1)
            .products(PRODUCTS_SET)
            .build();

    private static final CardDto CARD_1 = CardDto.builder()
            .number("1234 4567 8912 3456")
            .expiration("02/22")
            .userName("USER USER")
            .build();

    private static final OrderDto CREATED_ORDER = OrderDto.builder()
            .id(UUID.fromString("5596c91b-9e61-3129-87b6-8aa299028111"))
            .user(USER_1)
            .amount(2321)
            .shopAddress(SHOP_ADDRESS_1)
            .products(PRODUCTS_SET)
            .build();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsersService usersService;

    @MockBean
    private OrdersService ordersService;

    @BeforeEach
    void setUp() {
        when(usersService.getOrders(UUID.fromString("4896c91b-9e61-3129-87b6-8aa299028058")))
                .thenReturn(ORDER_1);
        when(ordersService.save(UUID.fromString("4896c91b-9e61-3129-87b6-8aa299028058"), CARD_1, SHOP_ADDRESS_1))
                .thenReturn(CREATED_ORDER);
    }

    @Test
    public void return_orders() throws Exception {
        mockMvc.perform(get("/orders")
                .param("user-id", "4896c91b-9e61-2345-87b6-8aa299028058"))
                .andExpect(status().isOk());
    }

    @Test
    public void add_order() throws Exception {
        mockMvc.perform(post("/ordering")
                .param("user-id", "4896c91b-9e61-2345-87b6-8aa299028058")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"cardDto\": \"1234 4567 8912 3456\", \n" +
                        "  \"shopAddressDto\": \"MOSCOV\"\n" +
                        "}"
                ))
                .andExpect(status().isOk());
    }
}