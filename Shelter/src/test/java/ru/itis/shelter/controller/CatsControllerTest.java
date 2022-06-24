package ru.itis.shelter.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import ru.itis.shelter.api.CatsController;
import ru.itis.shelter.dao.BlackListRepository;
import ru.itis.shelter.services.CatsService;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CatsController.class)
@DisplayName("CatsController is working when ...")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class CatsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CatsService catsService;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private BlackListRepository blackListRepository;

    @Value("${test.token}")
    private String token;

    @Test
    public void return_403_without_token() throws Exception {
        mockMvc.perform(get("/cats/get-all")
                        .param("page", "0"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void return_405_when_health_wrong_format() throws Exception {
        mockMvc.perform(post("/cats/update")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"health\": \"health\",\n" +
                                "  \"state\" : \"ON_SHELTER\"\n" +
                                "  \"catId\" :  1 \n" +
                                "}"))
                .andExpect(status().is(405))
                .andDo(print());
    }

}
