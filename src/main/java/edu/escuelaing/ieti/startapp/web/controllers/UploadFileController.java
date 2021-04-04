package edu.escuelaing.ieti.startapp.web.controllers;

import edu.escuelaing.ieti.startapp.business.exception.FileServiceException;
import edu.escuelaing.ieti.startapp.business.services.fileservices.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "api/v1/files")
public class UploadFileController {
    private final IFileService fileService;

    @Autowired
    public UploadFileController(IFileService fileService) {
        this.fileService = fileService;
    }
    @PostMapping("/image")
    public ResponseEntity<Object> uploadImage(@RequestParam("folder")String folder,
                                              @RequestParam("fileName") String fileName,
                                              @RequestParam("file") MultipartFile image){
        ResponseEntity<Object> response;
        try {
            String fileUrl = fileService.uploadImage("ieti-2021/"+folder,fileName.toLowerCase(),image);
            response =  new ResponseEntity<>(fileUrl,HttpStatus.CREATED);
        } catch (FileServiceException e) {
            Map<String,Object> errorResponse = new HashMap<>();
            errorResponse.put("Error",e.getMessage());
            response = new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        return response;
    }
}
