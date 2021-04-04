package edu.escuelaing.ieti.startapp.business.services.fileservices;

import edu.escuelaing.ieti.startapp.business.exception.FileServiceException;
import edu.escuelaing.ieti.startapp.web.requests.UploadFileRequest;
import org.springframework.web.multipart.MultipartFile;

public interface IFileService {
    String uploadFile(UploadFileRequest request, MultipartFile file) throws FileServiceException;
}
