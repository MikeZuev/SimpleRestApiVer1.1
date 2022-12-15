package test;

import net.test.tomcat.app.entities.User;
import net.test.tomcat.app.repository.hibernateimpl.UserHibernate;
import net.test.tomcat.app.services.Impl.UserServiceImpl;
import net.test.tomcat.app.services.UserService;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TestUserService {

    private UserService userService = new UserServiceImpl(new UserHibernate());

    @Test
    public void TestUserGetById(){
        User user = userService.getById(1);
        System.out.println(user);


    }

    @Test
    public void TestUserGetAll(){
        List<User> users = userService.getAll();
        users.stream().forEach(System.out::println);
    }

    @Test
    public void TestSaveNewUser(){
        User user = new User("Jack");
        userService.save(user);
    }

    @Test
    public void TestUpdateUser(){
        User user = userService.getById(1);
        user.setName("JackBackSell");
        userService.update(user);
    }

    @Test
    public void TestDeleteUser(){
        userService.delete(5);
    }
}

