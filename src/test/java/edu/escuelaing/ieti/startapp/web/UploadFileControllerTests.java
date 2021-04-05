package edu.escuelaing.ieti.startapp.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.escuelaing.ieti.startapp.business.exception.FileServiceException;
import edu.escuelaing.ieti.startapp.business.services.fileservices.IFileService;
import edu.escuelaing.ieti.startapp.business.services.fileservices.impl.FileServiceImpl;
import edu.escuelaing.ieti.startapp.web.controllers.UploadFileController;
import edu.escuelaing.ieti.startapp.web.requests.UploadFileRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.net.http.HttpResponse;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc

class UploadFileControllerTests {

    private IFileService fileService = Mockito.mock(FileServiceImpl.class);
    private UploadFileController uploadFileController = new UploadFileController(fileService);


    @Test
    void shouldUploadFile() throws FileServiceException {
        MockMultipartFile file = new MockMultipartFile("file","example.pdf","application/pdf","test".getBytes());
        UploadFileRequest request = new UploadFileRequest("ieti-2021/tests","example.pdf","DOCUMENT");
        when(fileService.uploadFile(Mockito.any(),Mockito.any())).thenReturn("s3.amazonaws.com/ieti-2021/test/example.docx");
        ResponseEntity<Object> response = uploadFileController.uploadFile(request,file);
        Assertions.assertEquals(HttpStatus.CREATED,response.getStatusCode());
    }
    @Test
    void shouldNotUploadFile() throws FileServiceException {
        MockMultipartFile file = new MockMultipartFile("file","example.jpgh","application/pdf","testError".getBytes());
        UploadFileRequest request = new UploadFileRequest("ieti-2021/tests","example.jpgh","IMAGE");
        when(fileService.uploadFile(Mockito.any(),Mockito.any())).thenThrow(new FileServiceException(FileServiceException.INVALID_EXTENSION));
        ResponseEntity<Object> response = uploadFileController.uploadFile(request,file);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
    }

}
