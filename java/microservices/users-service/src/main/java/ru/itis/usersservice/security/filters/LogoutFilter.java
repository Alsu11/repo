package ru.itis.usersservice.security.filters;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.itis.usersservice.dao.mongo.BlackListRepository;
import ru.itis.usersservice.models.JwtToken;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
public class LogoutFilter extends OncePerRequestFilter {

    private final BlackListRepository blackListRepository;

    private final static RequestMatcher logoutRequest = new AntPathRequestMatcher("/login", "GET");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (logoutRequest.matches(request)) {
            log.info("Logout");
            String tokenHeader = request.getHeader("Authorization");
            if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
                JwtToken token = JwtToken.builder()
                        .id(UUID.randomUUID())
                        .jwt(tokenHeader.substring("Bearer ".length()))
                        .build();
                blackListRepository.save(token);
                log.info("Token in the black list");
                SecurityContextHolder.getContext();
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
