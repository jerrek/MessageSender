package ru.digital.legue.messagesender.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.digital.legue.messagesender.entity.FilePathEntity;
import ru.digital.legue.messagesender.repository.FilePathRepository;

import javax.annotation.Resource;

@Service
@Transactional
public class FilePathService {

    @Resource(name = "filePathRepository")
    private FilePathRepository repository;

    public String getPathToProps() {
        if (repository.findOne(0L) != null) {
            return repository.findOne(0L).getPath();
        }
        return "";
    }

    public void savePathToProps(String path) {
        repository.save(new FilePathEntity(0L, path));
    }
}
