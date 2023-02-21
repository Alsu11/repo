package ru.itis.usersservice.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.itis.usersservice.dao.jpa.SupportRepository;
import ru.itis.usersservice.dao.jpa.UsersRepository;
import ru.itis.usersservice.dto.SupportDto;
import ru.itis.usersservice.exceptions.Errors;
import ru.itis.usersservice.exceptions.UsersException;
import ru.itis.usersservice.models.SupportEntity;
import ru.itis.usersservice.services.SupportService;
import ru.itis.usersservice.utils.mappers.SupportMapper;

import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
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
