package ru.itis.utils.mappers;

import java.io.IOException;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.models.FileInfoEntity;
import ru.itis.models.FileInfoEntity.FileInfoEntityBuilder;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-31T12:38:55+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.14.1 (Amazon.com Inc.)"
)
@Component
public class FilesMapperImpl implements FilesMapper {

    @Override
    public FileInfoEntity toRequest(MultipartFile multipartFile) throws IOException {
        if ( multipartFile == null ) {
            return null;
        }

        FileInfoEntityBuilder fileInfoEntity = FileInfoEntity.builder();

        if ( multipartFile.getContentType() != null ) {
            fileInfoEntity.type( multipartFile.getContentType() );
        }
        if ( multipartFile.getBytes() != null ) {
            fileInfoEntity.binary( toBinary( multipartFile.getBytes() ) );
        }
        fileInfoEntity.size( multipartFile.getSize() );

        return fileInfoEntity.build();
    }
}
