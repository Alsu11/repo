package ru.itis.usersservice.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.itis.usersservice.dao.mongo.BlackListRepository;
import ru.itis.usersservice.dto.UserDto;
import ru.itis.usersservice.exceptions.TokensException;
import ru.itis.usersservice.security.details.UserInfo;
import ru.itis.usersservice.services.TokensService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.security.SignatureException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;
    private final TokensService tokensService;
    private final BlackListRepository blackListRepository;

    private final List<String> ignore = Arrays.asList("/sign-in", "/sign-up");

    private final String uuid_regex = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-5][0-9a-f]{3}-[089ab][0-9a-f]{3}-[0-9a-f]{12}$";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (ignore.contains(request.getRequestURI())) {
            filterChain.doFilter(request, response);
        } else {
            String tokenHeader = request.getHeader("Authorization");

            if (tokenHeader == null) {
                logger.warn("Токен утерян");
                sendForbidden(response, "Токен утерян");
            } else if (tokenHeader.startsWith("Bearer ")) {
                String token = tokenHeader.substring("Bearer ".length());

                if (blackListRepository.findByJwt(token).isPresent()) {
                    logger.warn("Токен в черном списке");
                    filterChain.doFilter(request, response);
                }

                if (checkRefresh(token)) {
                    updateRefresh(token, response);
                }

                try {
                    logger.info("Расшифровка JWT токена");
                    UserInfo user = tokensService.parseAccessToken(token);

                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(user.getEmail(), token,
                                    Collections.singletonList(
                                            new SimpleGrantedAuthority(user.getRole().toString())));

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);

                } catch (Exception e) {
                    sendForbidden(response, "Время жизни токена истекло или токен неверный" + e.getMessage());
                }
            } else {
                logger.warn("Неверный формат токена");
                sendForbidden(response, "Неверный формат токена");
            }
        }
    }

    private void sendForbidden(HttpServletResponse response, String message) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        objectMapper.writeValue(response.getWriter(), Collections.singletonMap("error", message));
    }

    private boolean checkRefresh(String token) {
        Pattern pattern = Pattern.compile(uuid_regex);
        Matcher matcher = pattern.matcher(token);
        return matcher.matches();
    }

    private void updateRefresh(String token, HttpServletResponse response) throws IOException {
        logger.info("Обновление рфереш токена");
        try {
            UserDto userDto = tokensService.updateRefreshToken(token);
            objectMapper.writeValue(response.getWriter(), userDto);
        } catch (TokensException | IOException e) {
            sendForbidden(response, e.getMessage());
        }
    }

}
