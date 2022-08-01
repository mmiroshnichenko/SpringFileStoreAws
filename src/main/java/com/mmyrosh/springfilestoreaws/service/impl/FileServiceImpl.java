package com.mmyrosh.springfilestoreaws.service.impl;

import com.mmyrosh.springfilestoreaws.model.File;
import com.mmyrosh.springfilestoreaws.model.Status;
import com.mmyrosh.springfilestoreaws.repository.FileRepository;
import com.mmyrosh.springfilestoreaws.service.FileService;
import com.mmyrosh.springfilestoreaws.service.FileStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@Slf4j
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;
    private final FileStoreService fileStoreService;

    @Value("${aws.bucket.name}")
    private String bucketName;

    @Autowired
    public FileServiceImpl(FileRepository fileRepository, FileStoreService fileStoreService) {
        this.fileRepository = fileRepository;
        this.fileStoreService = fileStoreService;
    }

    @Override
    public File saveFile(String title, String description, MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file");
        }

        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));

        String path = String.format("%s/%s", bucketName, UUID.randomUUID());
        String fileName = String.format("%s", file.getOriginalFilename());
        try {
            fileStoreService.upload(path, fileName, Optional.of(metadata), file.getInputStream());
        } catch (IOException e) {
            throw new IllegalStateException("Failed to upload file", e);
        }
        File fileRecord = new File();
        fileRecord.setTitle(title);
        fileRecord.setDescription(description);
        fileRecord.setName(fileName);
        fileRecord.setPath(path);
        fileRecord.setCreated(new Date());
        fileRecord.setUpdated(new Date());
        fileRecord.setStatus(Status.ACTIVE);

        File savedFile = fileRepository.save(fileRecord);
        log.info("IN save - file: {} successfully saved", savedFile);
        return savedFile;
    }

    @Override
    public List<File> getAll() {
        List<File> result = fileRepository.findAll();
        log.info("IN getAll - {} files found", result.size());
        return result;
    }

    @Override
    public byte[] downloadFile(Long id) {
        File file = getFileById(id);

        return fileStoreService.download(file.getPath(), file.getName());
    }

    @Override
    public void delete(File file) {
        fileStoreService.delete(file.getPath(), file.getName());
    }

    @Override
    public File getFileById(Long id) {
        File result = fileRepository.findById(id).orElse(null);
        if (result == null) {
            log.info("IN findById - no file found by id: {}", id);
            return null;
        }
        log.info("IN findById - file: {} found by id: {}", result, id);
        return result;
    }
}
