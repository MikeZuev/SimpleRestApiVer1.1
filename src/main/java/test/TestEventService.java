package test;

import net.test.tomcat.app.entities.Event;
import net.test.tomcat.app.repository.hibernateimpl.EventHibernate;
import net.test.tomcat.app.services.EventService;
import net.test.tomcat.app.services.Impl.EventServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TestEventService {

    private EventService eventService = new EventServiceImpl(new EventHibernate());

    @Test
    public void TestEventGetById(){
        Event event = eventService.getById(1);
        System.out.println(event);
    }

    @Test
    public void TestEventGetAll(){
        List<Event> events = eventService.getAll();
        events.stream().forEach(System.out::println);
    }

    @Test
    public void TestEventSave(){
        Event event = new Event(1,1);
        Event eventTwo = new Event(2,2);
        eventService.save(event);

    }

    @Test
    public void TestEventUpdate(){
        Event event = eventService.getById(1);
        event.setFileId(3);
        event.setUserId(3);
        eventService.update(event);

    }

    @Test
    public void TestEventDelete(){
        eventService.delete(2);
    }


}
