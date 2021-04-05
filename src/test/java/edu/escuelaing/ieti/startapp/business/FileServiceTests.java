package edu.escuelaing.ieti.startapp.business;

import com.amazonaws.services.s3.AmazonS3;
import edu.escuelaing.ieti.startapp.business.exception.FileServiceException;
import edu.escuelaing.ieti.startapp.business.services.fileservices.IFileService;
import edu.escuelaing.ieti.startapp.business.services.fileservices.impl.FileServiceImpl;
import edu.escuelaing.ieti.startapp.clients.S3Client;
import edu.escuelaing.ieti.startapp.web.requests.UploadFileRequest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.AnonymousAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import io.findify.s3mock.S3Mock;
import org.springframework.mock.web.MockMultipartFile;

import static org.mockito.Mockito.when;

class FileServiceTests {
    static S3Mock mockS3;
    static S3Client mockS3Client = Mockito.mock(S3Client.class);
    IFileService fileService = new FileServiceImpl(mockS3Client);
    @BeforeAll
    static void setUp(){
        mockS3 = new S3Mock.Builder().withPort(8001).withInMemoryBackend().build();
        mockS3.start();
        EndpointConfiguration endpoint = new EndpointConfiguration("http://localhost:8001", "us-east-1");
        AmazonS3 client = AmazonS3ClientBuilder
                .standard()
                .withPathStyleAccessEnabled(true)
                .withEndpointConfiguration(endpoint)
                .withCredentials(new AWSStaticCredentialsProvider(new AnonymousAWSCredentials()))
                .build();
        client.createBucket("ieti-2021-testsenvironment");
        when(mockS3Client.getConnection()).thenReturn(client);
        S3Client.getInstance();
    }
    @Test
    void shouldStartEmbeddedS3(){
        AmazonS3 s3Client = mockS3Client.getConnection();
        Assertions.assertEquals("us-east-1",s3Client.getRegionName());
    }
    @Test
    void shouldUploadDocumentFile() throws FileServiceException {
        UploadFileRequest request = new UploadFileRequest("ieti-2021-testsenvironment","example.pdf","DOCUMENT");
        MockMultipartFile file = new MockMultipartFile("file","example.pdf","application/pdf","test".getBytes());
        String url = fileService.uploadFile(request,file);
        Assertions.assertEquals("http://localhost:8001/ieti-2021-testsenvironment/example.pdf",url);
    }
    @Test
    void shouldUploadImageFile() throws FileServiceException {
        UploadFileRequest request = new UploadFileRequest("ieti-2021-testsenvironment","example.jpg","IMAGE");
        MockMultipartFile file = new MockMultipartFile("file","example.jpg","image/jpeg","test".getBytes());
        String url = fileService.uploadFile(request,file);
        Assertions.assertEquals("http://localhost:8001/ieti-2021-testsenvironment/example.jpg",url);
    }
    @Test
    void shouldNotUploadFile() {
        UploadFileRequest request = new UploadFileRequest("ieti-2021-testsenvironment","example.pdf","NONE");
        MockMultipartFile file = new MockMultipartFile("file","example.pdfh","application/pdfh","test".getBytes());
        try {
            fileService.uploadFile(request,file);
        } catch (FileServiceException e) {
            Assertions.assertEquals(FileServiceException.INVALID_EXTENSION,e.getMessage());
        }
    }
    @AfterAll
    static void shutdown(){
        mockS3.shutdown();
    }
}
