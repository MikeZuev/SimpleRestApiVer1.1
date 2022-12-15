package test;

import net.test.tomcat.app.entities.File;
import net.test.tomcat.app.repository.hibernateimpl.FileHibernate;
import net.test.tomcat.app.services.FileService;
import net.test.tomcat.app.services.Impl.FileServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TestFileService {

    private FileService fileService = new FileServiceImpl(new FileHibernate());

    @Test
    public void TestFileGetById(){
        File file = fileService.getById(1);
        System.out.println(file);

    }

    @Test
    public void TestFileGetAll(){
        List<File> files = fileService.getAll();
        files.stream().forEach(System.out::println);
    }

    @Test
    public void TestFileSave(){
        File file = new File("supergame", "UNC\\ComputerName\\SharedFolder\\Resource.txt");
        fileService.save(file);
    }

    @Test
    public void TestFileUpdate(){
        File file = fileService.getById(6);
        file.setName("supergamechangedname");
        file.setFilePath("c/programfile/startfolder/skype.exe");
        fileService.update(file);
    }

    @Test
    public void TestDeleteUser(){
        fileService.delete(6);
    }

}
