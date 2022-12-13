package net.test.tomcat.app.servlets.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.test.tomcat.app.entities.User;
import net.test.tomcat.app.repository.UserRepository;
import net.test.tomcat.app.repository.hibernateimpl.UserHibernate;
import net.test.tomcat.app.services.Impl.UserServiceImpl;
import net.test.tomcat.app.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;




public class ServletForAll extends HttpServlet {


    private UserService userService;

    public ServletForAll(){
        userService = new UserServiceImpl(new UserHibernate());

    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = request.getRequestURI();
        System.out.println(url);

        String[] subStr;
        String delimeter = "/";
        subStr = url.split(delimeter);


        for(int i = 0; i < subStr.length; i++) {
            if(subStr[i].equals("all")){
                List usersList = userService.getAll();
                Gson gson = new GsonBuilder().setPrettyPrinting().create();

                String json = gson.toJson(usersList);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                PrintWriter writer = response.getWriter();

                writer.println(json);
            }
            if(subStr[i].contains("id")){

                String delim = "=";
                String[] strs;
                strs = subStr[i].split(delim);
                Integer id = Integer.parseInt(strs[strs.length - 1]);


                User user = userService.getById(id);
                String json = new Gson().toJson(user);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                PrintWriter writer = response.getWriter();

                writer.println(json);

            }
            if(subStr[i].equals("one")){
                User user = userService.getById(1);
                String json = new Gson().toJson(user);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                PrintWriter writer = response.getWriter();

                writer.println(json);
            }

        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userBody = getBody(request);

        System.out.println("Try to write some data: ===================");
        System.out.println(userBody);


        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(userBody, User.class);
        userService.save(user);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userBody = getBody(req);

        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(userBody, User.class);
        userService.update(user);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userBody = getBody(req);

        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(userBody, User.class);
        int id = user.getId();
        System.out.println(id);
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