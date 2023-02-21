package ru.itis.usersservice.api.controllers;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import ru.itis.usersservice.client.EmailClient;
import ru.itis.usersservice.dao.jpa.EmailConfirmationRepository;
import ru.itis.usersservice.dao.jpa.PasswordChangeConfirmationRepository;
import ru.itis.usersservice.dao.jpa.RefreshTokenRepository;
import ru.itis.usersservice.dao.jpa.UsersRepository;
import ru.itis.usersservice.dao.mongo.BlackListRepository;
import ru.itis.usersservice.dto.EmailDto;
import ru.itis.usersservice.dto.PasswordDto;
import ru.itis.usersservice.dto.enums.Role;
import ru.itis.usersservice.dto.enums.State;
import ru.itis.usersservice.exceptions.ConfirmException;
import ru.itis.usersservice.exceptions.Errors;
import ru.itis.usersservice.models.EmailConfirmationClassEntity;
import ru.itis.usersservice.models.PasswordChangeConfirmationClassEntity;
import ru.itis.usersservice.models.UserEntity;
import ru.itis.usersservice.security.details.UserDetailsServiceImpl;
import ru.itis.usersservice.security.details.UserInfo;
import ru.itis.usersservice.security.filters.JwtAuthorizationFilter;
import ru.itis.usersservice.services.EmailService;
import ru.itis.usersservice.services.PasswordChangeService;
import ru.itis.usersservice.services.TokensService;
import ru.itis.usersservice.utils.mappers.UsersMapper;

import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = PasswordChangeController.class, includeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {PasswordChangeService.class,PasswordEncoder.class})})
@DisplayName("PasswordChangeController")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PasswordChangeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private static PasswordChangeService passwordChangeService;

    @MockBean
    PasswordChangeConfirmationRepository passwordChangeConfirmationRepository;

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

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Value("${password.change.url}")
    String emailUrl;

    private static final String confirmToken = "123";

    private final Map<String, Object> data = new HashMap<String, Object>() {{
        put("user", user);
        put("confirmLink", emailUrl + confirmToken);
    }};
    private final UserEntity fraudUser = UserEntity.builder()
            .id(UUID.fromString("6a7e584f-f389-4dd3-8e9c-f8e6578017b2"))
            .email("user@mail.com")
            .state(State.CONFIRMED)
            .build();

    private final UserEntity user = UserEntity.builder()
            .id(UUID.fromString("6a7e584f-f389-4dd3-8e9c-f8e6578017b6"))
            .email("user@mail.com")
            .password("$2a$10$F7bjRWP.Gn9Zz.nEWkcU4elTMNubPAaIcz7keK6OuPVl5a7GbwPs.")
            .state(State.NOT_CONFIRMED)
            .build();

    private final UserEntity confirmedUser = UserEntity.builder()
            .id(UUID.fromString("6a7e584f-f389-4dd3-8e9c-f8e6578017b6"))
            .email("user@mail.com")
            .password("$2a$10$F7bjRWP.Gn9Zz.nEWkcU4elTMNubPAaIcz7keK6OuPVl5a7GbwPs.")
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

    private PasswordChangeConfirmationClassEntity passwordChange = PasswordChangeConfirmationClassEntity.builder()
            .confirmed(false)
            .user(confirmedUser)
            .newPassword("$2a$10$F7bjRWP.Gn9Zz.nEWkcU4elTMNubPAaIcz7keK6OuPVl5a7GbwPa.")
            .token(confirmToken)
            .build();

    /*
        private final PasswordDto passwordDto =
    */
/*            {\n" +
            "\"email\": \"user@mail.com\",\n" +
            "\"oldPassword\" : \"Password123!\",\n" +
            "\"newPassword\":\"Password123!!\" \n" +
            "}"))*/
    @BeforeEach
    void setUp() throws SignatureException {
        when(tokensService.parseAccessToken(token.substring("Bearer ".length()))).thenReturn(userInfo);
    }

    @Test
    public void return_403_without_auth() throws Exception {
        mockMvc.perform(post("/password-change")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "\"email\": \"user@mail.com\",\n" +
                                "\"oldPassword\" : \"Password123!\",\n" +
                                "\"newPassword\":\"Password123!!\" \n" +
                                "}"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void when_sending_token_to_email_client_is_successful() throws Exception {
        when(usersRepository.findByEmail("user@mail.com")).thenReturn(Optional.ofNullable(confirmedUser));
        mockMvc.perform(post("/password-change")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "\"email\": \"user@mail.com\",\n" +
                                "\"oldPassword\" : \"Password123!\",\n" +
                                "\"newPassword\":\"Password123!!\" \n" +
                                "}"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void when_email_is_not_confirmed() throws Exception {
        when(usersRepository.findByEmail("user@mail.com")).thenReturn(Optional.ofNullable(user));
        mockMvc.perform(post("/password-change")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "\"email\": \"user@mail.com\",\n" +
                                "\"oldPassword\" : \"Password123!\",\n" +
                                "\"newPassword\":\"Password123!!\" \n" +
                                "}"))
                .andExpect(status().is(473))
                .andDo(print());
    }

    @Test
    public void when_old_and_new_passwords_does_not_match() throws Exception {
        when(usersRepository.findByEmail("user@mail.com")).thenReturn(Optional.ofNullable(confirmedUser));
        mockMvc.perform(post("/password-change")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "\"email\": \"user@mail.com\",\n" +
                                "\"oldPassword\" : \"Password123\",\n" +
                                "\"newPassword\":\"Password123!!\" \n" +
                                "}"))
                .andExpect(status().is(450))
                .andDo(print());
    }
    @Test
    public void when_passwords_are_the_same() throws Exception {
        when(usersRepository.findByEmail("user@mail.com")).thenReturn(Optional.ofNullable(confirmedUser));
        mockMvc.perform(post("/password-change")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "\"email\": \"user@mail.com\",\n" +
                                "\"oldPassword\" : \"Password123!\",\n" +
                                "\"newPassword\":\"Password123!\" \n" +
                                "}"))
                .andExpect(status().is(406))
                .andDo(print());
    }
    @Test
    public void when_new_password_is_not_valid() throws Exception {
        when(usersRepository.findByEmail("user@mail.com")).thenReturn(Optional.ofNullable(confirmedUser));
        mockMvc.perform(post("/password-change")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "\"email\": \"user@mail.com\",\n" +
                                "\"oldPassword\" : \"Password123!\",\n" +
                                "\"newPassword\":\"password\" \n" +
                                "}"))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void when_password_change_is_successful() throws Exception {
        when(usersRepository.findByEmail("user@mail.com")).thenReturn(Optional.ofNullable(confirmedUser));
        when(passwordChangeConfirmationRepository.findByToken(confirmToken)).thenReturn(Optional.ofNullable(passwordChange));
        mockMvc.perform(get("/password-change/{confirm-code}", confirmToken)
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andDo(print());
    }
    @Test
    public void when_password_change_is_already_confirmed() throws Exception {
        when(usersRepository.findByEmail("user@mail.com")).thenReturn(Optional.ofNullable(confirmedUser));
        when(passwordChangeConfirmationRepository.findByToken(confirmToken)).thenReturn(Optional.empty());
        mockMvc.perform(get("/password-change/{confirm-code}", confirmToken)
                        .header("Authorization", token))
                .andExpect(status().is(405))
                .andDo(print());
    }
    @Test
    public void when_user_is_fraud() throws Exception {
        when(usersRepository.findByEmail("user@mail.com")).thenReturn(Optional.ofNullable(fraudUser));
        when(passwordChangeConfirmationRepository.findByToken(confirmToken)).thenReturn(Optional.ofNullable(passwordChange));
        mockMvc.perform(get("/password-change/{confirm-code}", confirmToken)
                        .header("Authorization", token))
                .andExpect(status().is(403))
                .andDo(print());
    }
}