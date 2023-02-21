package ru.itis.controllers;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.itis.dto.ProductDto;
import ru.itis.services.BasketService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.mockito.Mockito.when;

@WebMvcTest(BasketController.class)
@DisplayName("BasketController")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class BasketControllerTest {

    private static final ProductDto PRODUCT_1 = ProductDto.builder()
            .name("Concealer")
            .build();
    private static final ProductDto PRODUCT_2 = ProductDto.builder()
            .name("Ink")
            .build();
    private static final ProductDto PRODUCT_3 = ProductDto.builder()
            .name("Ink")
            .category("Cosmetic")
            .maker("Loreal Paris")
            .vendorCode("RK-26880")
            .price(234)
            .description("Cosmetic for women")
            .discount(0)
            .build();

    private static final Set<ProductDto> PRODUCTS_SET = new HashSet<>(Arrays.asList(PRODUCT_1, PRODUCT_2));

    @Autowired
    public MockMvc mockMvc;

    @MockBean
    private BasketService basketService;

    @BeforeEach
    void setUp() {
        when(basketService.getBasket(UUID.fromString("4896c91b-9e61-2345-87b6-8aa299028058")))
                .thenReturn(PRODUCTS_SET);
        when(basketService.addProduct(UUID.fromString("5596c91b-9e61-3129-87b6-8aa299028111"),
                UUID.fromString("5596c91b-9e61-3129-87b6-8aa299022222")))
                .thenReturn(PRODUCT_3);
    }

    @Test
    public void return_all_products_in_basket() throws Exception {
        mockMvc.perform(get("/{user-id}/basket")
                        .param("user-id", "4896c91b-9e61-2345-87b6-8aa299028058"))
                        .andExpect(status().isOk());
    }

    @Test
    public void add_product_in_basket() throws Exception {
        mockMvc.perform(post("/add/{product-id}")
                        .param("product-id", "4896c91b-9e61-3129-87b6-8aa299028058")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"name\": \"Ink\", \n" +
                                "  \"maker\": \"Loreal Paris\"\n" +
                                "  \"vendorCode\": \"RK-26880\"\n" +
                                "  \"price\": \"234\"\n" +
                                "  \"description\": \"Cosmetic for women\"\n" +
                                "  \"discount\": \"0\"\n" +
                                "  \"category\": \"Cosmetic\"\n" +
                                "}"
                                ))
                        .andExpect(status().isOk());
    }

    @Test
    public void delete_product_from_basket() throws Exception {
        mockMvc.perform(delete("/delete/{product-id}")
                        .param("product-id", "4896c91b-9e61-3129-87b6-8aa299028058"))
                        .andExpect(status().isOk());
    }
}