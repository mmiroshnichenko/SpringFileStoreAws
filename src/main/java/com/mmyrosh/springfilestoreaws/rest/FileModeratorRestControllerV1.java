package com.mmyrosh.springfilestoreaws.rest;

import com.mmyrosh.springfilestoreaws.dto.FileRequestDto;
import com.mmyrosh.springfilestoreaws.model.File;
import com.mmyrosh.springfilestoreaws.service.EventService;
import com.mmyrosh.springfilestoreaws.service.FileService;
import com.mmyrosh.springfilestoreaws.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/api/v1/moderation/files/")
public class FileModeratorRestControllerV1 {
    private final FileService fileService;
    private final EventService eventService;
    private final UserService userService;

    @Autowired
    public FileModeratorRestControllerV1(FileService fileService, EventService eventService, UserService userService) {
        this.fileService = fileService;
        this.eventService = eventService;
        this.userService = userService;
    }

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<File> saveFile(@RequestParam("title") String title,
                                         @RequestParam("description") String description,
                                         @RequestParam("file") MultipartFile file) {
        File savedFile= fileService.saveFile(title, description, file);
        eventService.addNew(savedFile, userService.getAuthUser());

        return new ResponseEntity<>(savedFile, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<File> getUserById(@PathVariable(name = "id") Long id) {
        File file = fileService.getFileById(id);
        eventService.delete(file, userService.getAuthUser());
        fileService.delete(file);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
