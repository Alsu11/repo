package ru.itis.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.dao.jpa.SupportRepository;
import ru.itis.dao.jpa.UsersRepository;
import ru.itis.dto.SupportDto;
import ru.itis.dto.UserDto;
import ru.itis.exceptions.Errors;
import ru.itis.exceptions.UsersException;
import ru.itis.models.SupportEntity;
import ru.itis.models.UserEntity;
import ru.itis.services.SupportService;
import ru.itis.utils.mappers.SupportMapper;
import ru.itis.utils.mappers.UsersMapper;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.reflections.util.ConfigurationBuilder.build;

@RequiredArgsConstructor
@Service
public class SupportServiceImpl implements SupportService {

    private final SupportRepository supportRepository;
    private final UsersRepository usersRepository;
    private final SupportMapper supportMapper;

    @Override
    public SupportDto addComment(UUID userId, SupportDto supportDto) {

        SupportEntity supportEntity = SupportEntity.builder()
                .user(usersRepository.findById(userId)
                        .orElseThrow(() -> new UsersException(Errors.USER_NOT_FOUND)))
                .message(supportDto.getMessage())
                .build();

        supportRepository.save(supportEntity);
        return supportMapper.toResponse(supportEntity);
    }
}
