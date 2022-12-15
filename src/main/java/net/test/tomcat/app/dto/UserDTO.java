package net.test.tomcat.app.dto;

import com.google.gson.annotations.Expose;
import net.test.tomcat.app.entities.Event;
import net.test.tomcat.app.entities.User;

import java.util.List;

public class UserDTO {

    @Expose
    private Integer id;

    @Expose
    private String name;

    @Expose
    private List<Event> events;

    public UserDTO(User entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.events = entity.getEvents();

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

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}

