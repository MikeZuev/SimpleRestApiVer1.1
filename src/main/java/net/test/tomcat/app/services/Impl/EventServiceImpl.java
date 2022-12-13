package net.test.tomcat.app.services.Impl;

import net.test.tomcat.app.entities.Event;
import net.test.tomcat.app.repository.EventRepository;
import net.test.tomcat.app.services.EventService;

import java.util.List;

public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }


    @Override
    public Event getById(Integer id) {
        return eventRepository.getById(id);
    }

    @Override
    public List<Event> getAll() {
        return eventRepository.getAll();
    }

    @Override
    public Event save(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public Event update(Event event) {
        return eventRepository.update(event);
    }

    @Override
    public void delete(Integer id) {
        eventRepository.delete(id);

    }
}
