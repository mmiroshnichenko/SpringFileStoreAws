package com.mmyrosh.springfilestoreaws.rest;

import com.mmyrosh.springfilestoreaws.dto.EventResponseDto;
import com.mmyrosh.springfilestoreaws.model.File;
import com.mmyrosh.springfilestoreaws.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/files/")
public class FileUserRestControllerV1 {
    private final FileService fileService;

    @Autowired
    public FileUserRestControllerV1(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public byte[] getFileById(@PathVariable(name = "id") Long id) {
        return fileService.downloadFile(id);
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<File>> getFiles() {
        return new ResponseEntity<>(fileService.getAll(), HttpStatus.OK);
    }

    @GetMapping(value="{id}/events", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EventResponseDto>> getFileEvents(@PathVariable(name = "id") Long id) {
        File file = fileService.getFileById(id);

        return new ResponseEntity<>(file.getEvents().stream()
                .map(event -> EventResponseDto.fromEvent(event)).collect(Collectors.toList()), HttpStatus.OK);
    }
}
