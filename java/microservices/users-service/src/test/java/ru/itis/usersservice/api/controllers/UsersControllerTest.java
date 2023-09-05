package ru.itis.usersservice.api.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import ru.itis.usersservice.api.UsersController;
import ru.itis.usersservice.dao.mongo.BlackListRepository;
import ru.itis.usersservice.services.UsersService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsersController.class)
@DisplayName("UsersController is working when ...")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class UsersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsersService usersService;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private BlackListRepository blackListRepository;

    private final String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2YTdlNTg0Zi1mMzg5LTRkZDMtOGU5Yy1mOGU2NTc4MDE3YjYiLCJyb2xlIjoiVVNFUiIsInJlZnJlc2giOiIxMTBlNmI1Ni04NjdhLTQwZjUtYjhiMC01ZTYwNWUxY2JjYzMiLCJzdGF0ZSI6Ik5PVF9DT05GSVJNRUQiLCJleHAiOjE2NTc2NTc4ODMsImVtYWlsIjoidXNlckBtYWlsLmNvbSJ9.bKHY6gynOttbCe7kLzzIwZvH04nnN_oKcXzMqvpkrSk";

    @Test
    public void return_453_when_email_is_taken() throws  Exception {
        mockMvc.perform(post("/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "   \"firstName\" : \"Alsu\", \n" +
                        "   \"lastName\" : \"Vika\", \n" +
                        "   \"email\" : \"alsu.yumadilova@gmail.com\", \n" +
                        "   \"phoneNumber\" : \"9962565380\", \n" +
                        "   \"password\" : \"100\" \n" +
                        "}"))
                .andExpect(status().is(453))
                .andDo(print());
    }

    @Test
    public void return_400_when_email_is_wrong() throws Exception {
        mockMvc.perform(post("/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "   \"firstName\" : \"Gold\", \n" +
                        "   \"lastName\" : \"Apple\", \n" +
                        "   \"email\" : \"gold.apple\", \n" +
                        "   \"phoneNumber\" : \"9962565380\", \n" +
                        "   \"password\" : \"200\" \n" +
                        "}"))
                .andExpect(status().is(400))
                .andDo(print());
    }

    @Test
    public void return_400_when_phoneNumber_is_large() throws Exception {
        mockMvc.perform(post("/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "   \"firstName\" : \"Gold\", \n" +
                                "   \"lastName\" : \"Apple\", \n" +
                                "   \"email\" : \"goldapple@gmail.com\", \n" +
                                "   \"phoneNumber\" : \"996256538055\", \n" +
                                "   \"password\" : \"200\" \n" +
                                "}"))
                .andExpect(status().is(400))
                .andDo(print());
    }


    @Test
    public void return_200_when_phoneNumber_is_large() throws Exception {
        mockMvc.perform(post("/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "   \"firstName\" : \"Gold\", \n" +
                                "   \"lastName\" : \"Apple\", \n" +
                                "   \"email\" : \"apple@gmail.com\", \n" +
                                "   \"phoneNumber\" : \"9962565380\", \n" +
                                "   \"password\" : \"200\" \n" +
                                "}"))
                .andExpect(status().is(400))
                .andDo(print());
    }
}
