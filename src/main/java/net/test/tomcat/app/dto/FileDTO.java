package net.test.tomcat.app.dto;

import com.google.gson.annotations.Expose;
import net.test.tomcat.app.entities.File;

public class FileDTO {
    @Expose
    private int id;
    @Expose
    private String name;
    @Expose
    private String filePath;



    public FileDTO (File entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.filePath = entity.getFilePath();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
