package com.mmyrosh.springfilestoreaws.service;


import com.mmyrosh.springfilestoreaws.model.File;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

    File saveFile(String title, String description, MultipartFile file);

    byte[] downloadFile(Long id);

    List<File> getAll();

    void delete(File file);

    File getFileById(Long id);
}
