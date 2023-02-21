package ru.itis.usersservice.services.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.usersservice.dao.jpa.RefreshTokenRepository;
import ru.itis.usersservice.dao.jpa.UsersRepository;
import ru.itis.usersservice.dto.UserDto;
import ru.itis.usersservice.exceptions.Errors;
import ru.itis.usersservice.exceptions.TokensException;
import ru.itis.usersservice.exceptions.UsersException;
import ru.itis.usersservice.models.RefreshTokenEntity;
import ru.itis.usersservice.models.UserEntity;
import ru.itis.usersservice.security.details.UserInfo;
import ru.itis.usersservice.services.TokensService;
import ru.itis.usersservice.utils.mappers.UsersMapper;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.SignatureException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class TokensServiceImpl implements TokensService {

    private final RefreshTokenRepository refreshRepository;
    private final UsersRepository usersRepository;
    private final UsersMapper usersMapper;

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.refreshExpirationMs}")
    private Long refreshTokenExpirationMs;

    @Value("${jwt.accessExpirationMs}")
    private Long accessTokenExpirationMs;

    @Override
    public UserDto createRefreshToken(String email) {
        log.info("Create refresh token");

        UserEntity userEntity = getUserByEmail(email);

        RefreshTokenEntity refreshTokenEntity = saveRefreshTokenToDb(userEntity);
        UserDto userDto = usersMapper.toResponse(userEntity);
        userDto.setRefreshToken(refreshTokenEntity.getId().toString());
        userDto.setAccessToken(createAccessToken(userEntity, refreshTokenEntity.getId()));

        return userDto;
    }

    @Override
    public UserInfo parseAccessToken(String token) throws SignatureException {
        log.info("Parse access token");
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(secretKey))
                .build().verify(token);
        UUID id = UUID.fromString(decodedJWT.getSubject());
        UserEntity user = usersRepository.findById(id)
                .orElseThrow(() -> new UsersException(Errors.USER_NOT_FOUND));
        UserInfo userInfo = UserInfo.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .state(user.getState())
                .role(user.getRole())
                .build();
        return userInfo;

    }

    @Transactional
    @Override
    public UserDto updateRefreshToken(String token) throws TokensException {
        log.info("Update refresh token");

        RefreshTokenEntity refreshToken = getRefreshTokenById(UUID.fromString(token));

        refreshRepository.delete(refreshToken);

        UserEntity userEntity = refreshToken.getUser();
        refreshRepository.deleteByUserId(userEntity.getId());

        return createRefreshToken(refreshToken.getUser().getEmail());

    }

    public RefreshTokenEntity saveRefreshTokenToDb(UserEntity userEntity) {
        log.info("Save refresh token");
        RefreshTokenEntity refreshTokenEntity = RefreshTokenEntity.builder()
                .user(userEntity)
                .expireDate(Instant.now().plusMillis(refreshTokenExpirationMs))
                .createDate(Instant.now())
                .build();

        return refreshRepository.save(refreshTokenEntity);
    }

    public String createAccessToken(UserEntity userEntity, UUID refreshId) {
        log.info("Creating access token");

        Date date = Date.from(Instant.now().plusSeconds(18000));

        return JWT.create()
                .withSubject(userEntity.getId().toString())
                .withClaim("email", userEntity.getEmail())
                .withClaim("role", userEntity.getRole().toString())
                .withClaim("state", userEntity.getState().toString())
                .withClaim("refresh", refreshId.toString())
                .withExpiresAt(date)
                .sign(Algorithm.HMAC256(secretKey));
    }

    private UserEntity getUserByEmail(String email) {
        log.info("Looking for user by email");
        return usersRepository.findByEmail(email)
                .orElseThrow(() -> new UsersException(Errors.USER_NOT_FOUND));
    }

    private RefreshTokenEntity getRefreshTokenById(UUID id) {
        log.info("Looking for refresh token by id");
        return refreshRepository.findById(id)
                .orElseThrow(() -> new TokensException(Errors.TOKEN_NOT_FOUND));
    }
}
