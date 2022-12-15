package net.test.tomcat.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.test.tomcat.app.dto.FileDTO;
import net.test.tomcat.app.entities.User;
import net.test.tomcat.app.repository.hibernateimpl.FileHibernate;
import net.test.tomcat.app.entities.File;
import net.test.tomcat.app.repository.hibernateimpl.UserHibernate;
import net.test.tomcat.app.services.FileService;
import net.test.tomcat.app.services.Impl.FileServiceImpl;
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


public class FileRestControllerV1 extends HttpServlet {

    private final FileService fileService = new FileServiceImpl(new FileHibernate());
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

            File file = fileService.getById(id);
            FileDTO fileDTO = new FileDTO(file);
            String json = new Gson().toJson(fileDTO);
            PrintWriter writer = response.getWriter();

            writer.println(json);

        } else {

            List<File> filesList = fileService.getAll();
            List<FileDTO> filesDTOList = filesList.stream().map(file -> {
                FileDTO fileDTO = new FileDTO(file);
                return fileDTO;
            }).collect(Collectors.toList());


            String json = GSON.toJson(filesDTOList);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();

            writer.println(json);


        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fileBody = getBody(request);

        File file = GSON.fromJson(fileBody, File.class);
        fileService.save(file);

        String fileJson = GSON.toJson(file);
        PrintWriter printWriter = response.getWriter();
        printWriter.println(fileJson);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileBody = getBody(req);


        File file = GSON.fromJson(fileBody, File.class);
        fileService.update(file);
        String fileJson = GSON.toJson(file);
        PrintWriter printWriter = resp.getWriter();
        printWriter.println(fileJson);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileBody = getBody(req);


        File file = GSON.fromJson(fileBody, File.class);
        int id = file.getId();
        fileService.delete(id);
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
