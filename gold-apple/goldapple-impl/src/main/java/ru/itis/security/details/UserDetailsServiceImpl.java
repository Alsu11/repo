package ru.itis.security.details;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.itis.dao.jpa.UsersRepository;
import ru.itis.exceptions.Errors;
import ru.itis.exceptions.UsersException;
import ru.itis.models.UserEntity;
import ru.itis.utils.mappers.UsersMapper;

@RequiredArgsConstructor
@Service()
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsersRepository usersRepository;
    private final UsersMapper usersMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = usersRepository.findByEmail(email)
                .orElseThrow(() -> new UsersException(Errors.USER_NOT_FOUND));

        UserInfo userInfo = usersMapper.toRequest(userEntity);

        return new UserDetailsImpl(userInfo);
    }
}
