package ru.itis.usersservice.api.controllers;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MockMvc;
import ru.itis.usersservice.client.EmailClient;
import ru.itis.usersservice.dao.jpa.EmailConfirmationRepository;
import ru.itis.usersservice.dao.jpa.RefreshTokenRepository;
import ru.itis.usersservice.dao.jpa.UsersRepository;
import ru.itis.usersservice.dao.mongo.BlackListRepository;
import ru.itis.usersservice.dto.EmailDto;
import ru.itis.usersservice.dto.PasswordDto;
import ru.itis.usersservice.dto.enums.Role;
import ru.itis.usersservice.dto.enums.State;
import ru.itis.usersservice.models.EmailConfirmationClassEntity;
import ru.itis.usersservice.models.UserEntity;
import ru.itis.usersservice.security.details.UserDetailsServiceImpl;
import ru.itis.usersservice.security.details.UserInfo;
import ru.itis.usersservice.security.filters.JwtAuthorizationFilter;
import ru.itis.usersservice.services.EmailService;
import ru.itis.usersservice.services.TokensService;
import ru.itis.usersservice.utils.mappers.UsersMapper;

import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = EmailConfirmationController.class, includeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = EmailService.class)})
@DisplayName("EmailConfirmationController")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class EmailConfirmationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private static EmailService emailService;

    @MockBean
    EmailConfirmationRepository emailConfirmationRepository;

    @MockBean
    private EmailClient emailClient;

    @MockBean
    private BlackListRepository blackListRepository;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private RefreshTokenRepository refreshTokenRepository;

    @MockBean
    private static UsersRepository usersRepository;

    @MockBean
    private UsersMapper usersMapper;

    @MockBean
    private TokensService tokensService;

    @Autowired
    private JwtAuthorizationFilter jwtAuthorizationFilter;

    private static final String confirmToken = "123";

    @Value("${email.confirm.url}")
    private String emailUrl;

    private final Map<String, Object> data = new HashMap<String, Object>() {{
        put("user", user);
        put("confirmLink", emailUrl + confirmToken);
    }};

    private final UserEntity user = UserEntity.builder()
            .id(UUID.fromString("6a7e584f-f389-4dd3-8e9c-f8e6578017b6"))
            .email("user@mail.com")
            .state(State.NOT_CONFIRMED)
            .build();

    private final UserEntity confirmedUser = UserEntity.builder()
            .id(UUID.fromString("6a7e584f-f389-4dd3-8e9c-f8e6578017b6"))
            .email("user@mail.com")
            .state(State.CONFIRMED)
            .build();

    private final UserEntity fraudUser = UserEntity.builder()
            .id(UUID.fromString("6a7e584f-f389-4dd3-8e9c-f8e6578017b2"))
            .email("user@mail.com")
            .state(State.CONFIRMED)
            .build();

    private final EmailDto emailDto = EmailDto.builder()
            .data(data)
            .to("user@mail.com")
            .subject("Email confirmation")
            .templateName("confirm_mail")
            .build();

    private final String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2YTdlNTg0Zi1mMzg5LTRkZDMtOGU5Yy1mOGU2NTc4MDE3YjYiLCJyb2xlIjoiVVNFUiIsInJlZnJlc2giOiIxMTBlNmI1Ni04NjdhLTQwZjUtYjhiMC01ZTYwNWUxY2JjYzMiLCJzdGF0ZSI6Ik5PVF9DT05GSVJNRUQiLCJleHAiOjE2NTc2NTc4ODMsImVtYWlsIjoidXNlckBtYWlsLmNvbSJ9.bKHY6gynOttbCe7kLzzIwZvH04nnN_oKcXzMqvpkrSk";

    private final UserInfo userInfo = UserInfo.builder()
            .email("user@mail.com")
            .password("$2a$10$F7bjRWP.Gn9Zz.nEWkcU4elTMNubPAaIcz7keK6OuPVl5a7GbwPs.")
            .role(Role.USER)
            .state(State.NOT_CONFIRMED)
            .build();

    private final EmailConfirmationClassEntity emailConfirmationClassEntity = EmailConfirmationClassEntity.builder()
            .user(user)
            .token(confirmToken)
            .build();

    private final EmailConfirmationClassEntity confirmedEmailConfirmationClassEntity = EmailConfirmationClassEntity.builder()
            .user(confirmedUser)
            .token(confirmToken)
            .build();

    @BeforeEach
    void setUp() throws SignatureException {
        when(tokensService.parseAccessToken(token.substring("Bearer ".length()))).thenReturn(userInfo);
        doNothing().when(emailConfirmationRepository).delete(emailConfirmationClassEntity);
    }

    @Test
    public void return_403_without_auth() throws Exception {
        mockMvc.perform(get("/email/confirmation"))
                .andExpect(status().isForbidden());
    }


    @Test
    public void when_sending_token_to_email_client_is_successful() throws Exception {
        when(usersRepository.findByEmail("user@mail.com")).thenReturn(Optional.ofNullable(user));
        when(emailConfirmationRepository.findByToken(confirmToken)).thenReturn(Optional.ofNullable(emailConfirmationClassEntity));
        mockMvc.perform(get("/email/confirmation")
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void when_user_is_confirmed_and_sending_is_unsuccessful() throws Exception {
        when(usersRepository.findByEmail("user@mail.com")).thenReturn(Optional.ofNullable(confirmedUser));
        when(emailConfirmationRepository.findByToken(confirmToken)).thenReturn(Optional.ofNullable(confirmedEmailConfirmationClassEntity));
        mockMvc.perform(get("/email/confirmation")
                        .header("Authorization", token))
                .andExpect(status().is(472))
                .andDo(print());
    }

    @Test
    void return_403_when_confirm_email_without_auth() throws Exception {
        mockMvc.perform(get("/email/confirmation/{confirm-code}", confirmToken))
                .andExpect(status().isForbidden());
    }

    @Test
    public void when_confirm_mail_is_successful() throws Exception {
        when(usersRepository.findByEmail("user@mail.com")).thenReturn(Optional.ofNullable(user));
        when(emailConfirmationRepository.findByToken(confirmToken)).thenReturn(Optional.ofNullable(emailConfirmationClassEntity));
        mockMvc.perform(get("/email/confirmation/{confirm-code}", confirmToken)
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(content().string("true"))
                .andDo(print());
    }

    @Test
    public void when_user_is_already_confirmed() throws Exception {
        when(usersRepository.findByEmail("user@mail.com")).thenReturn(Optional.ofNullable(confirmedUser));
        when(emailConfirmationRepository.findByToken(confirmToken)).thenReturn(Optional.ofNullable(confirmedEmailConfirmationClassEntity));
        mockMvc.perform(get("/email/confirmation/{confirm-code}", confirmToken)
                        .header("Authorization", token))
                .andExpect(status().is(472))
                .andDo(print());
    }

    @Test
    public void when_user_is_fraud() throws Exception {
        when(usersRepository.findByEmail("user@mail.com")).thenReturn(Optional.ofNullable(fraudUser));
        when(emailConfirmationRepository.findByToken(confirmToken)).thenReturn(Optional.ofNullable(confirmedEmailConfirmationClassEntity));
        mockMvc.perform(get("/email/confirmation/{confirm-code}", confirmToken)
                        .header("Authorization", token))
                .andExpect(status().is(403))
                .andDo(print());
    }

}