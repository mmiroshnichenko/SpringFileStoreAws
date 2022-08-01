package com.mmyrosh.springfilestoreaws.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FileRequestDto {
    private String title;
    private String description;
    private MultipartFile file;
}
