package ru.itis.services;

import ru.itis.dto.SupportDto;

import java.util.UUID;

public interface SupportService {
    SupportDto addComment(UUID userId, SupportDto supportDto);
}
