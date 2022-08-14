package ru.itis.services;

import ru.itis.dto.UserDto;
import ru.itis.exceptions.TokensException;

import java.security.SignatureException;

public interface TokensService {

    UserDto createRefreshToken(String email);

    String parseAccessToken(String token) throws SignatureException;

    UserDto updateRefreshToken(String token) throws TokensException;
}
