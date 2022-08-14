package ru.itis.dao.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.itis.models.FileInfoEntity;

import java.util.UUID;

public interface FilesRepository  extends MongoRepository<FileInfoEntity, UUID> {
}
