package edu.escuelaing.ieti.startapp.business.services.fileservices;

import edu.escuelaing.ieti.startapp.business.exception.FileServiceException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface IFileService {
    String uploadImage(String bucketName, String fileName, MultipartFile file) throws FileServiceException;
}
