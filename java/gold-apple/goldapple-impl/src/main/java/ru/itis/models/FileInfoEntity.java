package ru.itis.models;

import lombok.*;
import org.bson.types.Binary;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

/**
 * Файл
 */
@Data
@Builder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "files")
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
