package ru.itis.usersservice.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import ru.itis.usersservice.dto.SignInForm;
import ru.itis.usersservice.dto.UserDto;
import ru.itis.usersservice.security.details.UserDetailsImpl;
import ru.itis.usersservice.security.details.UserInfo;
import ru.itis.usersservice.services.TokensService;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validator;
import java.io.IOException;
import java.util.Set;

@Slf4j

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper;
    private final TokensService tokensService;

    public JwtAuthenticationFilter(AuthenticationManager manager, ObjectMapper objectMapper, TokensService tokensService) {
        super(manager);
        this.objectMapper = objectMapper;
        this.tokensService = tokensService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {

            SignInForm signInForm = objectMapper.readValue(request.getReader(), SignInForm.class);

            log.info("Attempt authentication - email {} " + signInForm.getEmail());

            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(signInForm.getEmail(), signInForm.getPassword());
            log.info(token.getCredentials().toString());
            Authentication auth = super.getAuthenticationManager().authenticate(token);
            SecurityContext sc = SecurityContextHolder.getContext();
            sc.setAuthentication(auth);
            return auth;

        } catch (IOException | AuthenticationException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException {

        logger.info("Authentication is ok");

        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();
        UserInfo userInfo = userDetails.getUser();

        UserDto userDto = tokensService.createRefreshToken(userInfo.getEmail());

        objectMapper.writeValue(response.getWriter(), userDto);
    }
}