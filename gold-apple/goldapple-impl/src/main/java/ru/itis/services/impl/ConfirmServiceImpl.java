package ru.itis.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.itis.dao.jpa.UsersRepository;
import ru.itis.dto.UserDto;
import ru.itis.dto.enums.State;
import ru.itis.exceptions.ConfirmException;
import ru.itis.exceptions.Errors;
import ru.itis.exceptions.UsersException;
import ru.itis.models.UserEntity;
import ru.itis.services.ConfirmService;
import ru.itis.utils.mappers.UsersMapper;

import java.sql.Time;
import java.time.Instant;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class ConfirmServiceImpl implements ConfirmService {

    private final UsersRepository usersRepository;
    private final UsersMapper usersMapper;

    @Value("${confirmTimePeriod}")
    private Long confirmTimePeriod;

    @Override
    public UserDto confirmAccount(String code) {

        UserEntity user = usersRepository.findUserEntityByConfirmCode(code)
                .orElseThrow(() -> new UsersException(Errors.USER_NOT_FOUND));

        if(user.getCreateDate().plusSeconds(confirmTimePeriod).isBefore(Instant.now())) {
            throw new ConfirmException(Errors.CODE_TIME_OUT);
        }

        user.setState(State.CONFIRMED);
        usersRepository.save(user);

        return usersMapper.toResponse(user);
    }
}

