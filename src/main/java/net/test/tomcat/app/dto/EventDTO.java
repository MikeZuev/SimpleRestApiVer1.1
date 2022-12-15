package net.test.tomcat.app.dto;

import net.test.tomcat.app.entities.Event;
import net.test.tomcat.app.entities.File;

import java.util.List;

public class EventDTO {

    private Integer id;

    private Integer fileId;

    private Integer userId;

    private List<File> files;

    public EventDTO(Event entity) {
        this.id = entity.getId();
        this.fileId = entity.getFileId();
        this.userId = entity.getUserId();
        this.files = entity.getFiles();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }
}
