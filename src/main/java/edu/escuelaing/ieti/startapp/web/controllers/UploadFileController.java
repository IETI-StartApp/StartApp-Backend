package edu.escuelaing.ieti.startapp.web.controllers;

import edu.escuelaing.ieti.startapp.business.exception.FileServiceException;
import edu.escuelaing.ieti.startapp.business.services.fileservices.IFileService;
import edu.escuelaing.ieti.startapp.web.requests.UploadFileRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins= "*")
@RequestMapping(value = "api/v1/files")
public class UploadFileController {
    private final IFileService fileService;

    @Autowired
    public UploadFileController(IFileService fileService) {
        this.fileService = fileService;
    }
    @PostMapping()
    public ResponseEntity<Object> uploadFile(@RequestPart("config") UploadFileRequest request,
                                              @RequestPart("file") MultipartFile file
                                              ){
        ResponseEntity<Object> response;
        try {
            String fileUrl = fileService.uploadFile(request,file);
            response =  new ResponseEntity<>(fileUrl,HttpStatus.CREATED);
        } catch (FileServiceException e) {
            Map<String,Object> errorResponse = new HashMap<>();
            errorResponse.put("Error",e.getMessage());
            response = new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        return response;
    }
}
