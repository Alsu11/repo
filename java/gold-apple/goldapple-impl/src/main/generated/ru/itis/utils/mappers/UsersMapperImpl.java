package ru.itis.utils.mappers;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.itis.dto.UserDto;
import ru.itis.dto.UserDto.UserDtoBuilder;
import ru.itis.models.UserEntity;
import ru.itis.models.UserEntity.UserEntityBuilder;
import ru.itis.security.details.UserInfo;
import ru.itis.security.details.UserInfo.UserInfoBuilder;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-31T12:38:56+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.14.1 (Amazon.com Inc.)"
)
@Component
public class UsersMapperImpl implements UsersMapper {

    @Override
    public UserDto toResponse(UserEntity user) {
        if ( user == null ) {
            return null;
        }

        UserDtoBuilder userDto = UserDto.builder();

        if ( user.getFirstName() != null ) {
            userDto.firstName( user.getFirstName() );
        }
        if ( user.getLastName() != null ) {
            userDto.lastName( user.getLastName() );
        }
        if ( user.getEmail() != null ) {
            userDto.email( user.getEmail() );
        }
        if ( user.getPhoneNumber() != null ) {
            userDto.phoneNumber( user.getPhoneNumber() );
        }
        if ( user.getHashPassword() != null ) {
            userDto.hashPassword( user.getHashPassword() );
        }
        if ( user.getDiscount() != null ) {
            userDto.discount( user.getDiscount() );
        }
        if ( user.getAvatar() != null ) {
            userDto.avatar( user.getAvatar() );
        }

        return userDto.build();
    }

    @Override
    public UserEntity toRequest(UserDto user) {
        if ( user == null ) {
            return null;
        }

        UserEntityBuilder<?, ?> userEntity = UserEntity.builder();

        if ( user.getFirstName() != null ) {
            userEntity.firstName( user.getFirstName() );
        }
        if ( user.getLastName() != null ) {
            userEntity.lastName( user.getLastName() );
        }
        if ( user.getEmail() != null ) {
            userEntity.email( user.getEmail() );
        }
        if ( user.getPhoneNumber() != null ) {
            userEntity.phoneNumber( user.getPhoneNumber() );
        }
        if ( user.getHashPassword() != null ) {
            userEntity.hashPassword( user.getHashPassword() );
        }
        if ( user.getDiscount() != null ) {
            userEntity.discount( user.getDiscount() );
        }
        if ( user.getAvatar() != null ) {
            userEntity.avatar( user.getAvatar() );
        }

        return userEntity.build();
    }

    @Override
    public UserInfo toRequest(UserEntity user) {
        if ( user == null ) {
            return null;
        }

        UserInfoBuilder userInfo = UserInfo.builder();

        if ( user.getEmail() != null ) {
            userInfo.email( user.getEmail() );
        }
        if ( user.getState() != null ) {
            userInfo.state( user.getState() );
        }
        if ( user.getRole() != null ) {
            userInfo.role( user.getRole() );
        }

        return userInfo.build();
    }
}
