package ru.itis.utils.mappers;

import org.bson.types.Binary;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.models.FileInfoEntity;

import java.io.IOException;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface FilesMapper {
    @Mapping(target = "type", source = "multipartFile.contentType")
    @Mapping(target = "binary", source = "bytes", qualifiedByName = "toBinary")
    FileInfoEntity toRequest(MultipartFile multipartFile) throws IOException;

    @Named(value = "toBinary")
    default Binary toBinary(byte[] bytes) {
        return new Binary(bytes);
    }
}
