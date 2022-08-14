package ru.itis.utils.mappers;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.itis.dto.AddCardDto;
import ru.itis.dto.CardDto;
import ru.itis.dto.CardDto.CardDtoBuilder;
import ru.itis.models.CardEntity;
import ru.itis.models.CardEntity.CardEntityBuilder;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-31T12:38:56+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.14.1 (Amazon.com Inc.)"
)
@Component
public class CardsMapperImpl implements CardsMapper {

    @Override
    public CardEntity toRequest(AddCardDto addCardDto) {
        if ( addCardDto == null ) {
            return null;
        }

        CardEntityBuilder<?, ?> cardEntity = CardEntity.builder();

        if ( addCardDto.getNumber() != null ) {
            cardEntity.number( addCardDto.getNumber() );
        }
        if ( addCardDto.getExpiration() != null ) {
            cardEntity.expiration( addCardDto.getExpiration() );
        }
        if ( addCardDto.getCvv() != null ) {
            cardEntity.cvv( addCardDto.getCvv() );
        }
        if ( addCardDto.getUserName() != null ) {
            cardEntity.userName( addCardDto.getUserName() );
        }

        return cardEntity.build();
    }

    @Override
    public CardDto toResponse(CardEntity cardEntity) {
        if ( cardEntity == null ) {
            return null;
        }

        CardDtoBuilder cardDto = CardDto.builder();

        if ( cardEntity.getNumber() != null ) {
            cardDto.number( cardEntity.getNumber() );
        }
        if ( cardEntity.getExpiration() != null ) {
            cardDto.expiration( cardEntity.getExpiration() );
        }
        if ( cardEntity.getUserName() != null ) {
            cardDto.userName( cardEntity.getUserName() );
        }

        return cardDto.build();
    }
}
