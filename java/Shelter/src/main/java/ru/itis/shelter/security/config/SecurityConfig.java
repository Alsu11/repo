package ru.itis.shelter.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.itis.shelter.dao.BlackListRepository;
import ru.itis.shelter.security.filters.JwtAuthenticationFilter;
import ru.itis.shelter.security.filters.JwtAuthorizationFilter;
import ru.itis.shelter.security.filters.LogoutFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String SIGN_IN = "/sign-in";
    public static final String SIGN_UP = "/sign-up";

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

    @Autowired
    private UserDetailsService userDetailsServiceImpl;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BlackListRepository repository;

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Autowired
    public PasswordEncoder passwordEncoder;

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
                new JwtAuthenticationFilter(authenticationManagerBean(), objectMapper, secretKey);

        JwtAuthorizationFilter authorizationFilter = new JwtAuthorizationFilter(objectMapper, secretKey, repository);

        LogoutFilter logoutFilter = new LogoutFilter(repository);

        authenticationFilter.setFilterProcessesUrl(SIGN_IN);

        http.csrf().disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilter(authenticationFilter);
        http.addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(logoutFilter, JwtAuthenticationFilter.class);

        http.authorizeRequests()
                .antMatchers(SIGN_UP + "/**").permitAll()
                .antMatchers(  SIGN_IN + "/**").permitAll()
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/cats/**").authenticated()
                .antMatchers("/shelter/**").authenticated()
                .anyRequest().authenticated();
    }

}

