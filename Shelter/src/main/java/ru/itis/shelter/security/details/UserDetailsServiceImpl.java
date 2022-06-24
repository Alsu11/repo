package ru.itis.shelter.security.details;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.itis.shelter.dao.UsersRepository;
import ru.itis.shelter.exceptions.Errors;
import ru.itis.shelter.exceptions.UsersException;
import ru.itis.shelter.models.UserEntity;


@RequiredArgsConstructor
@Service()
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = usersRepository.findByEmail(email)
                .orElseThrow(() -> new UsersException(Errors.USER_NOT_FOUND));

        return new UserDetailsImpl(userEntity);
    }
}
