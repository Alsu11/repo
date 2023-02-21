package ru.itis.usersservice.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.web.filter.CharacterEncodingFilter;
import ru.itis.usersservice.dao.mongo.BlackListRepository;
import ru.itis.usersservice.security.details.UserDetailsServiceImpl;
import ru.itis.usersservice.security.filters.JwtAuthenticationFilter;
import ru.itis.usersservice.security.filters.JwtAuthorizationFilter;
import ru.itis.usersservice.security.filters.LogoutFilter;
import ru.itis.usersservice.services.TokensService;

import java.nio.charset.StandardCharsets;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String SIGN_IN = "/sign-in";
    public static final String SIGN_UP = "/sign-up";
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

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private final UserDetailsService userDetailsServiceImpl;
    @Autowired
    private ObjectMapper objectMapper;
    private final BlackListRepository repository;
    private final TokensService tokensService;

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsServiceImpl);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter authenticationFilter =
                new JwtAuthenticationFilter(authenticationManagerBean(), objectMapper, tokensService);

        JwtAuthorizationFilter authorizationFilter = new JwtAuthorizationFilter(objectMapper, tokensService, repository);

        LogoutFilter logoutFilter = new LogoutFilter(repository);

        authenticationFilter.setFilterProcessesUrl(SIGN_IN);

        http.csrf().disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilter(authenticationFilter);
        http.addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(logoutFilter, JwtAuthenticationFilter.class);
        http.addFilterBefore(new CharacterEncodingFilter(StandardCharsets.UTF_8.name(), true), authorizationFilter.getClass());


        http.authorizeRequests()
                .antMatchers(SIGN_UP + "/**").permitAll()
                .antMatchers(SIGN_IN + "/**").permitAll()
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers(WILDCARD).authenticated();    }

}
