package ru.itis.services.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.itis.dao.jpa.RefreshTokenRepository;
import ru.itis.dao.jpa.UsersRepository;
import ru.itis.dto.UserDto;
import ru.itis.exceptions.Errors;
import ru.itis.exceptions.TokensException;
import ru.itis.exceptions.UsersException;
import ru.itis.models.RefreshTokenEntity;
import ru.itis.models.UserEntity;
import ru.itis.services.TokensService;
import ru.itis.utils.mappers.UsersMapper;

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
        UserEntity userEntity = getUserEmail(email);

        RefreshTokenEntity refreshTokenEntity = saveRefreshTokenToDb(userEntity);

        UserDto userDto = usersMapper.toResponse(userEntity);
        userDto.setRefreshToken(refreshTokenEntity.getId().toString());
        userDto.setAccessToken(createAccessToken(userEntity, refreshTokenEntity.getId()));

        return userDto;
    }

    @Override
    public String parseAccessToken(String token) throws SignatureException {
        Jws<Claims> jwt = Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token);

        return jwt.getBody().get("role", String.class);
    }

    @Override
    public UserDto updateRefreshToken(String token) throws TokensException {
        RefreshTokenEntity refreshToken = getRefreshTokenById(UUID.fromString(token));

        refreshRepository.delete(refreshToken);

        if(refreshToken.getExpireDate().compareTo(Instant.now()) > 0) {
            return createRefreshToken(refreshToken.getUser().getEmail());
        } else {
            throw new TokensException(Errors.EXPIRED_TOKEN);
        }
    }

    public RefreshTokenEntity saveRefreshTokenToDb(UserEntity userEntity) {
        RefreshTokenEntity refreshTokenEntity = RefreshTokenEntity.builder()
                .user(userEntity)
                .expireDate(Instant.now().plusMillis(refreshTokenExpirationMs))
                .createDate(Instant.now())
                .build();

        return refreshRepository.save(refreshTokenEntity);
    }

    public String createAccessToken(UserEntity userEntity, UUID refreshId) {

        Key key = getKey();

        return Jwts.builder()
                .claim("email", userEntity.getEmail())
                .claim("role", userEntity.getRole())
                .claim("state", userEntity.getState().toString())
                .claim("refresh", String.valueOf(refreshId))
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(accessTokenExpirationMs, ChronoUnit.MINUTES)))
                .signWith(key)
                .compact();
    }

    private UserEntity getUserEmail(String email) {
        return usersRepository.findByEmail(email)
                .orElseThrow(() -> new UsersException(Errors.USER_NOT_FOUND));
    }

    private RefreshTokenEntity getRefreshTokenById(UUID id) {
        return refreshRepository.findById(id)
                .orElseThrow(() -> new TokensException(Errors.TOKEN_NOT_FOUND));
    }
    private Key getKey() {
        return new SecretKeySpec(Base64.getDecoder().decode(secretKey),
                SignatureAlgorithm.HS256.getJcaName());
    }
}
