package net.test.tomcat.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.test.tomcat.app.dto.EventDTO;
import net.test.tomcat.app.entities.Event;

import net.test.tomcat.app.entities.User;
import net.test.tomcat.app.repository.hibernateimpl.EventHibernate;
import net.test.tomcat.app.repository.hibernateimpl.FileHibernate;
import net.test.tomcat.app.services.EventService;
import net.test.tomcat.app.services.FileService;
import net.test.tomcat.app.services.Impl.EventServiceImpl;
import net.test.tomcat.app.services.Impl.FileServiceImpl;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class EventRestControllerV1 extends HttpServlet {

    private final EventService eventService = new EventServiceImpl(new EventHibernate());
    private final Gson GSON = new GsonBuilder().setPrettyPrinting().create();




    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = request.getRequestURI();
        System.out.println(url);

        String[] subStr;
        String delimeter = "/";
        subStr = url.split(delimeter);

        if (subStr.length >= 5) {
            Integer id = Integer.parseInt(subStr[4]);

            System.out.println(id);

            Event event = eventService.getById(id);
            EventDTO eventDTO = new EventDTO(event);
            String json = new Gson().toJson(eventDTO);
            PrintWriter writer = response.getWriter();

            writer.println(json);

        } else {

            List<Event> eventsList = eventService.getAll();
            List<EventDTO> eventsDTOList = eventsList.stream().map(event -> {
                EventDTO eventDTO = new EventDTO(event);
                return eventDTO;
            }).collect(Collectors.toList());


            String json = GSON.toJson(eventsDTOList);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();

            writer.println(json);


        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userBody = getBody(request);

        System.out.println("Try to write some data: ===================");
        System.out.println(userBody);



        Event event = GSON.fromJson(userBody, Event.class);
        eventService.save(event);
        String eventJson = GSON.toJson(event);
        PrintWriter printWriter = response.getWriter();
        printWriter.println(eventJson);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userBody = getBody(req);


        Event event = GSON.fromJson(userBody, Event.class);
        eventService.update(event);

        String eventJson = GSON.toJson(event);
        PrintWriter printWriter = resp.getWriter();
        printWriter.println(eventJson);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userBody = getBody(req);


        Event event = GSON.fromJson(userBody, Event.class);
        int id = event.getId();
        System.out.println(id);
        eventService.delete(id);
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
