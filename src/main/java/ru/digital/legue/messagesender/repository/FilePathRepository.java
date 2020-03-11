package ru.digital.legue.messagesender.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.digital.legue.messagesender.entity.FilePathEntity;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface FilePathRepository extends CrudRepository<FilePathEntity, Long> {

}
