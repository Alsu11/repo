package ru.itis.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.itis.dto.SignInForm;
import ru.itis.dto.UserDto;
import ru.itis.security.details.UserDetailsImpl;
import ru.itis.security.details.UserInfo;
import ru.itis.services.TokensService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

            log.info("Attempt authentication - email {}", signInForm.getEmail());

            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(signInForm.getEmail(), signInForm.getPassword());

            return super.getAuthenticationManager().authenticate(token);

        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException {

        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();
        UserInfo userInfo = userDetails.getUser();

        UserDto userDto = tokensService.createRefreshToken(userInfo.getEmail());

        objectMapper.writeValue(response.getWriter(), userDto);
    }
}