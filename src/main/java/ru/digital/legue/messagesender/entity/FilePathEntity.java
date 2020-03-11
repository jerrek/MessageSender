package ru.digital.legue.messagesender.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FILE_PATH")
public class FilePathEntity {
    @Id
    private Long id;
    @Column(name = "PATH", nullable = false)
    private String path;

    public FilePathEntity() {
    }

    public FilePathEntity(Long id, String path) {
        this.id = id;
        this.path = path;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
