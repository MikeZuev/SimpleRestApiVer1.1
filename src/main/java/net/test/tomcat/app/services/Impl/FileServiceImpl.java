package net.test.tomcat.app.services.Impl;

import net.test.tomcat.app.entities.File;
import net.test.tomcat.app.repository.FileRepository;
import net.test.tomcat.app.services.FileService;

import java.util.List;

public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    public FileServiceImpl(FileRepository fileRepository){
        this.fileRepository = fileRepository;
    }

    @Override
    public File getById(Integer id) {
        return fileRepository.getById(id);
    }

    @Override
    public List<File> getAll() {
        return fileRepository.getAll();
    }

    @Override
    public File save(File file) {
        return fileRepository.save(file);
    }

    @Override
    public File update(File file) {
        return fileRepository.update(file);
    }

    @Override
    public void delete(Integer id) {
        fileRepository.delete(id);

    }
}
