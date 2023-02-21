package ru.itis.filesservice.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.itis.filesservice.dao.mongo.BlackListRepository;
import ru.itis.filesservice.security.filters.JwtAuthorizationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String WILDCARD = "/**";
    private static final String[] IGNORE = {
            "/account-swagger/api-docs",
            "/swagger-ui/index.html#/**",
            "/v3/api-docs",
            "/swagger-ui/**"
    };

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(IGNORE);
    }

    private final ObjectMapper objectMapper;
    private final BlackListRepository repository;
    @Value("${jwt.secretKey}")
    private final String secretKey;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        JwtAuthorizationFilter authorizationFilter = new JwtAuthorizationFilter(objectMapper, repository, secretKey);

        http.csrf().disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);

        http.authorizeRequests()
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers(WILDCARD).authenticated()
                .antMatchers("products/**/upload").hasRole("ADMIN");
    }
}
