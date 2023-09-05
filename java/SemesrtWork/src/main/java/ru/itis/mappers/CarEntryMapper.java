package ru.itis.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.itis.dto.CarEntryDto;
import ru.itis.dto.EntryForm;
import ru.itis.models.CarEntry;

@Mapper(uses = {UserMapper.class})
public interface CarEntryMapper {
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "address", source = "parking.address")
    CarEntryDto toResponse(CarEntry carEntry);

    @Mapping(target = "user.id", source = "userId")
    CarEntry toRequest(EntryForm entryForm);
}
