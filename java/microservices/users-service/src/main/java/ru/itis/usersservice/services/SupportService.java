package ru.itis.usersservice.services;

import ru.itis.usersservice.dto.SupportDto;

import java.util.UUID;

public interface SupportService {
    SupportDto addComment(UUID userId, SupportDto supportDto);
}
