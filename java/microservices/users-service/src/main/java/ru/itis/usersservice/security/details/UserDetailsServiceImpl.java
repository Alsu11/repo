package ru.itis.usersservice.security.details;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.itis.usersservice.dao.jpa.UsersRepository;
import ru.itis.usersservice.dto.UserDto;
import ru.itis.usersservice.exceptions.Errors;
import ru.itis.usersservice.exceptions.UsersException;
import ru.itis.usersservice.models.UserEntity;
import ru.itis.usersservice.utils.mappers.UsersMapper;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsersRepository usersRepository;
    private final UsersMapper usersMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("Load user by email: " + email + " from db");
        UserEntity userEntity = usersRepository.findByEmail(email)
                .orElseThrow(() -> new UsersException(Errors.USER_NOT_FOUND));

        UserInfo userInfo = usersMapper.toRequest(userEntity);

        log.info(userInfo.getEmail());
        UserDetails userDetails = new UserDetailsImpl(userInfo);
        log.info(userDetails.getUsername() + " " + userDetails.getPassword() + " " + userDetails.getAuthorities());

        return new UserDetailsImpl(userInfo);
    }

}
