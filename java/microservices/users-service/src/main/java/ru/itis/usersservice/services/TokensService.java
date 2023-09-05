package ru.itis.usersservice.services;

import ru.itis.usersservice.dto.UserDto;
import ru.itis.usersservice.exceptions.TokensException;
import ru.itis.usersservice.security.details.UserInfo;

import java.security.SignatureException;

public interface TokensService {

    UserDto createRefreshToken(String email);

    UserInfo parseAccessToken(String token) throws SignatureException;

    UserDto updateRefreshToken(String token) throws TokensException;
}
