package com.mmyrosh.springfilestoreaws.service;

import com.mmyrosh.springfilestoreaws.model.File;
import com.mmyrosh.springfilestoreaws.model.Status;
import com.mmyrosh.springfilestoreaws.repository.FileRepository;
import com.mmyrosh.springfilestoreaws.service.impl.FileServiceImpl;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HexFormat;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class FileServiceTest {
    private FileRepository mockFileRepository = Mockito.mock(FileRepository.class);
    private FileStoreService mockFileStoreService = Mockito.mock(FileStoreService.class);

    private FileService fileService = new FileServiceImpl(mockFileRepository, mockFileStoreService);

    private byte[] getContent() {
        return HexFormat.of().parseHex("e04fd020ea3a6910a2d808002b30309d");
    }

    private MultipartFile getMultipathFile() {
        byte[] content = getContent();
        return new MockMultipartFile("fileName", "originalFilename", "IMAGE", content);
    }

    private File getFile() {
        File file = new File();
        file.setId(1L);
        file.setTitle("title");
        file.setDescription("description");
        file.setName("fileName");
        file.setPath("path");
        file.setCreated(new Date());
        file.setUpdated(new Date());
        file.setStatus(Status.ACTIVE);

        return file;
    }

    @Test
    public void shouldSaveFileTest() {
        Mockito.doNothing().when(mockFileStoreService).upload(ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(),
                ArgumentMatchers.any());

        File file = getFile();
        Mockito.when(mockFileRepository.save(ArgumentMatchers.any())).thenReturn(file);

        File savedMockFile = fileService.saveFile("title", "descrition", getMultipathFile());
        assertEquals(file, savedMockFile);
;    }

    @Test
    public void shouldDownloadFileTest() {
        byte[] content = getContent();
        Mockito.when(mockFileRepository.findById(1L)).thenReturn(Optional.of(getFile()));
        Mockito.when(mockFileStoreService.download("path", "fileName")).thenReturn(content);
        byte[] downloadedContent = fileService.downloadFile(1L);

        assertEquals(content, downloadedContent);
    }
}
