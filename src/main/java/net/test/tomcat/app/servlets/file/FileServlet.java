package net.test.tomcat.app.servlets.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jndi.toolkit.url.Uri;
import net.test.tomcat.app.repository.hibernateimpl.FileHibernate;
import net.test.tomcat.app.entities.File;
import net.test.tomcat.app.services.FileService;
import net.test.tomcat.app.services.Impl.FileServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import java.net.URI;
import java.net.URL;
import java.util.List;




public class FileServlet extends HttpServlet {

    private FileService fileService;

    public FileServlet(){
        fileService = new FileServiceImpl(new FileHibernate());
    }




    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = request.getRequestURI();
        System.out.println(url);

        String[] subStr;
        String delimeter = "/";
        subStr = url.split(delimeter);


        for(int i = 0; i < subStr.length; i++) {
            if(subStr[i].equals("all")){
                List usersFile = fileService.getAll();
                Gson gson = new GsonBuilder().setPrettyPrinting().create();

                String json = gson.toJson(usersFile);
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


                File file = fileService.getById(id);
                String json = new Gson().toJson(file);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                PrintWriter writer = response.getWriter();

                writer.println(json);

            }
            if(subStr[i].equals("one")){
                File file = fileService.getById(1);


//                java.io.File file1 = new java.io.File(file.getFilePath());
//
//                URI uri = file1.toURI();
//
//                URL url1 = uri.toURL();
//
//                file.setFileUrl(String.valueOf(url1));

                String json = new Gson().toJson(file);
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


        System.out.println(userBody);


        ObjectMapper objectMapper = new ObjectMapper();
        File file = objectMapper.readValue(userBody, File.class);
        fileService.save(file);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userBody = getBody(req);

        ObjectMapper objectMapper = new ObjectMapper();
        File file = objectMapper.readValue(userBody, File.class);
        fileService.update(file);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userBody = getBody(req);

        ObjectMapper objectMapper = new ObjectMapper();
        File file = objectMapper.readValue(userBody, File.class);
        int id = file.getId();
        System.out.println(id);
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