package ru.itis.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import ru.itis.dto.AddCardDto;
import ru.itis.dto.CardDto;
import ru.itis.models.CardEntity;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface CardsMapper {
    CardEntity toRequest(AddCardDto addCardDto);
    CardDto toResponse(CardEntity cardEntity);
}
