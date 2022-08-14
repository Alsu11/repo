package ru.itis.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.itis.dto.UserDto;
import ru.itis.exceptions.TokensException;
import ru.itis.services.TokensService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.SignatureException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    private final TokensService tokensService;

    private final List<String> ignore = Arrays.asList("/sign-in", "/sign-up");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(ignore.contains(request.getRequestURI())) {
            filterChain.doFilter(request, response);
        }

        String tokenHeader = request.getHeader("Authorization");

        if (tokenHeader == null) {
            logger.warn("Токен утерян");
            sendForbidden(response, "Токен утерян");
        } else if (tokenHeader.startsWith("Bearer ")) {
            String token = tokenHeader.substring("Bearer ".length());

            try {
                String role = tokensService.parseAccessToken(token);

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(token, null,
                                Collections.singletonList(
                                        new SimpleGrantedAuthority(role)));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                filterChain.doFilter(request, response);

            } catch (SignatureException e) {
                sendForbidden(response, "Время жизни токена истекло");
            }

        } else if(tokenHeader.startsWith("Refresh ")) {

            try {
                String token = tokenHeader.substring("Refresh ".length());
                UserDto userDto = tokensService.updateRefreshToken(token);
                objectMapper.writeValue(response.getWriter(), userDto);
            } catch (TokensException e) {
                sendForbidden(response, e.getMessage());
            }
        }
        else {
            logger.warn("Неверный формат токена");
            sendForbidden(response, "Неверный формат токена");
        }
    }

    private void sendForbidden(HttpServletResponse response, String message) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        objectMapper.writeValue(response.getWriter(), Collections.singletonMap("error", message));
    }

}
