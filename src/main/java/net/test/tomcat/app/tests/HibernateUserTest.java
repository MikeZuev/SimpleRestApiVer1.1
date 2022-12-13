package net.test.tomcat.app.tests;

import net.test.tomcat.app.entities.User;
import net.test.tomcat.app.repository.hibernateimpl.UserHibernate;
import org.junit.jupiter.api.Test;

import java.util.List;


public class HibernateUserTest {
    UserHibernate hibernate = new UserHibernate();

    @Test
    public void UserGetByIdTest(){
        User user = hibernate.getById(1);
        System.out.println(user.toString());
    }

    @Test
    public void GetListOfUsersTest(){
        List users = hibernate.getAll();
        for(Object user: users){
            System.out.println(user.toString());
        }
    }





}
