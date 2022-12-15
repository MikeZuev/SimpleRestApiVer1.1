package net.test.tomcat.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.test.tomcat.app.dto.UserDTO;
import net.test.tomcat.app.entities.User;
import net.test.tomcat.app.repository.hibernateimpl.UserHibernate;
import net.test.tomcat.app.services.Impl.UserServiceImpl;
import net.test.tomcat.app.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class UserRestControllerV1 extends HttpServlet {


    private final UserService userService = new UserServiceImpl(new UserHibernate());
    private final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = request.getRequestURI();

        String[] subStr;
        String delimeter = "/";
        subStr = url.split(delimeter);
        for(String i: subStr){
            System.out.println(i);
        }
        System.out.println(subStr.length);


        //api/v1/users
        //api/v1/users/1
        //TODO: fix the path and checks
        if (subStr.length >= 5) {
            Integer id = Integer.parseInt(subStr[4]);

            System.out.println(id);

            User user = userService.getById(id);
            UserDTO userDTO = new UserDTO(user);
            String json = new Gson().toJson(userDTO);
            PrintWriter writer = response.getWriter();

            writer.println(json);

        } else {

                List<User> usersList = userService.getAll();
                List<UserDTO> usersDTOList = usersList.stream().map(user -> {
                    UserDTO userDTO = new UserDTO(user);
                    return userDTO;
                }).collect(Collectors.toList());


                String json = GSON.toJson(usersDTOList);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                PrintWriter writer = response.getWriter();

                writer.println(json);

            }
        }



    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userBody = getBody(request);

        User user = GSON.fromJson(userBody, User.class);
        userService.save(user);
        //TODO: return created object
        String userJson = GSON.toJson(user);
        PrintWriter printWriter = response.getWriter();
        printWriter.println(userJson);


    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userBody = getBody(req);


        User user = GSON.fromJson(userBody, User.class);
        userService.update(user);
        //TODO: return updated object
        String userJson = GSON.toJson(user);
        PrintWriter printWriter = resp.getWriter();
        printWriter.println(userJson);

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userBody = getBody(req);
        //TODO: remove ObjectMapper as we work with Gson
        User user = GSON.fromJson(userBody, User.class);
        int id = user.getId();
        userService.delete(id);
    }

    public static String getBody(HttpServletRequest request) throws IOException {

        String body = null;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }

        body = stringBuilder.toString();
        return body;
    }
}
