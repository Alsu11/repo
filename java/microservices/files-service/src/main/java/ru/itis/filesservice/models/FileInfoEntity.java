package ru.itis.filesservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

/**
 * Файл
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "file_info")
public class FileInfoEntity {

    /** Идентификатор файла */
    @Id
    private UUID id;

    /** Файл */
    private Binary binary;

    /** Размер файла */
    private Long size;

    /** Тип файла */
    private String type;

}

