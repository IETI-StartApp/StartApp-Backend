package edu.escuelaing.ieti.startapp.business.services.fileservices.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import edu.escuelaing.ieti.startapp.business.exception.FileServiceException;
import edu.escuelaing.ieti.startapp.business.services.fileservices.IFileService;
import edu.escuelaing.ieti.startapp.clients.S3Client;
import edu.escuelaing.ieti.startapp.web.requests.UploadFileRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class FileServiceImpl implements IFileService {
    private final S3Client s3Client;
    private final List<String> imageTypes;
    private final List<String> documentTypes;
    @Autowired
    public FileServiceImpl(S3Client s3Client){
        this.s3Client = s3Client;
        imageTypes = Arrays.asList("image/png", "image/jpeg", "image/jpg", "image/gif");
        documentTypes = Arrays.asList("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                "application/pdf", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
    }

    @Override
    public String uploadFile(UploadFileRequest request, MultipartFile multipartFile) throws FileServiceException {

        String fileUrl;
        if (isValid(multipartFile.getContentType(),request.getType())){
            fileUrl = upload(request.getFolder(), request.getFilename(), multipartFile);
        }else{
            throw new FileServiceException(FileServiceException.INVALID_EXTENSION);
        }
        return fileUrl;
    }

    private String upload(String bucketName, String fileName, MultipartFile multipartFile) throws FileServiceException {
        String fileUrl;
        try {
            fileUrl = uploadFileWithPublicAccess(bucketName, fileName, multipartFile);
        } catch (IOException e) {
            throw new FileServiceException(FileServiceException.UPLOAD_ERROR);
        }
        return fileUrl;
    }

    private String uploadFileWithPublicAccess (String bucketName, String fileName, MultipartFile multipartFile) throws IOException {
        AmazonS3 connection = s3Client.getConnection();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(multipartFile.getContentType());
        metadata.setContentLength(multipartFile.getSize());
        PutObjectRequest fileRequest = new PutObjectRequest(bucketName,
                fileName,multipartFile.getInputStream(),metadata)
                .withCannedAcl(CannedAccessControlList.PublicRead);
        connection.putObject(fileRequest);
        return connection.getUrl(bucketName,fileName).toExternalForm();
    }

    private boolean isValid(String contentType,String requestType){
        switch (requestType){
            case "IMAGE":
                return imageTypes.contains(contentType);
            case "DOCUMENT":
                return documentTypes.contains(contentType);
            default:
                return false;
        }

    }
}
