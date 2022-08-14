package ru.itis.utils.mappers;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.itis.dto.SupportDto;
import ru.itis.dto.SupportDto.SupportDtoBuilder;
import ru.itis.models.SupportEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-31T12:38:55+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.14.1 (Amazon.com Inc.)"
)
@Component
public class SupportMapperImpl implements SupportMapper {

    @Override
    public SupportDto toResponse(SupportEntity support) {
        if ( support == null ) {
            return null;
        }

        SupportDtoBuilder supportDto = SupportDto.builder();

        if ( support.getMessage() != null ) {
            supportDto.message( support.getMessage() );
        }

        return supportDto.build();
    }
}
