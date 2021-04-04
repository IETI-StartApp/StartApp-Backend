package edu.escuelaing.ieti.startapp.business.services.fileservices.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import edu.escuelaing.ieti.startapp.business.exception.FileServiceException;
import edu.escuelaing.ieti.startapp.business.services.fileservices.IFileService;
import edu.escuelaing.ieti.startapp.clients.S3Client;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class FileServiceImpl implements IFileService {
    private final S3Client s3Client;
    private final List<String> imageTypes;
    public FileServiceImpl(){
        s3Client = S3Client.getInstance();
        imageTypes = Arrays.asList("image/png", "image/jpeg", "image/jpg", "image/gif");
    }

    @Override
    public String uploadImage(String bucketName, String fileName, MultipartFile multipartFile) throws FileServiceException {

        String fileUrl;
        String mimeType = generateMimeType("image",fileName);
        if (isImage(mimeType)){
            try {
                fileUrl = uploadFileWithPublicAccess(bucketName, fileName, multipartFile);
            } catch (IOException e) {
                throw new FileServiceException(FileServiceException.UPLOAD_ERROR);
            }
        }else{
            throw new FileServiceException(FileServiceException.INVALID_IMAGE);
        }
        return fileUrl;
    }
    private String uploadFileWithPublicAccess (String bucketName, String fileName, MultipartFile multipartFile) throws IOException {
        AmazonS3 connection = s3Client.getConnection();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(multipartFile.getContentType());
        metadata.setContentLength(multipartFile.getSize());
        PutObjectRequest fileRequest = new PutObjectRequest(bucketName,
                fileName.toLowerCase(),multipartFile.getInputStream(),metadata)
                .withCannedAcl(CannedAccessControlList.PublicRead);
        connection.putObject(fileRequest);
        return connection.getUrl(bucketName,fileName).toExternalForm();
    }
    private boolean isImage(String contentType){
        return imageTypes.contains(contentType.toLowerCase().trim());
    }
    private String generateMimeType(String type, String fileName){
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        return String.format("%s/%s",type,extension);
    }
}
