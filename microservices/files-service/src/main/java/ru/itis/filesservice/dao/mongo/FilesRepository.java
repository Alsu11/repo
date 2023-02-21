package ru.itis.filesservice.dao.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.itis.filesservice.models.FileInfoEntity;

import java.util.UUID;

public interface FilesRepository extends MongoRepository<FileInfoEntity, UUID> {
}
