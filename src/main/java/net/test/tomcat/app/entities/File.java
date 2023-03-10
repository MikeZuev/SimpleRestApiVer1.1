package net.test.tomcat.app.entities;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.*;

@Entity
@Table(name="file")
@JsonPropertyOrder({"id", "name", "path"})
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "path")
    private String filePath;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "file_id")
    private Event event;




    public File(){

    }

    public File(String name, String filePath){
        this.name = name;
        this.filePath = filePath;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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





    @Override
    public String toString() {
        return "File{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
