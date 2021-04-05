package edu.escuelaing.ieti.startapp.clients;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import org.springframework.stereotype.Component;

@Component
public class S3Client {
    private static S3Client clientInstance;
    private final AmazonS3 client;

    private S3Client(){
        String accessKey = System.getenv("AWS_ACCESS_KEY_ID");
        String secretAccessKey = System.getenv("AWS_SECRET_ACCESS_KEY");
        String sessionToken = System.getenv("AWS_SECRET_SESSION_TOKEN");
        AWSCredentials  credentials = new BasicSessionCredentials(accessKey, secretAccessKey, sessionToken);
        client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_EAST_1)
                .build();
    }
    public static S3Client getInstance(){
        if (clientInstance == null){
            clientInstance = new S3Client();
        }
        return clientInstance;
    }
    public AmazonS3 getConnection(){
        return client;
    }




}
