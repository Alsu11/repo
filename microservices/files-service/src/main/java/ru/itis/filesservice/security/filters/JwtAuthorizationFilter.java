package ru.itis.filesservice.security.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.itis.filesservice.dao.mongo.BlackListRepository;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.SignatureException;
import java.util.Collections;

@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;
    private final BlackListRepository blackListRepository;
    private final String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader = request.getHeader("Authorization");

        if (tokenHeader == null) {
            logger.warn("Токен утерян");
            sendForbidden(response, "Токен утерян");
        } else if (tokenHeader.startsWith("Bearer ")) {
            String token = tokenHeader.substring("Bearer ".length());

            if (blackListRepository.findByJwt(token).isPresent()) {
                logger.warn("Токен в черном списке");
                filterChain.doFilter(request, response);
            } try {
                logger.info("Расшифровка JWT токена");

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(token, null,
                                Collections.singletonList(
                                        new SimpleGrantedAuthority(parseAccessToken(token))));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                filterChain.doFilter(request, response);

            } catch (SignatureException e) {
                sendForbidden(response, "Время жизни токена истекло");
            }


        } else {
            logger.warn("Неверный формат токена");
            sendForbidden(response, "Неверный формат токена");
        }
    }

    private void sendForbidden(HttpServletResponse response, String message) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        objectMapper.writeValue(response.getWriter(), Collections.singletonMap("error", message));
    }


    private String parseAccessToken(String token) throws SignatureException {

        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(secretKey))
                .build().verify(token);

        return decodedJWT.getClaim("role").asString();
    }

}
