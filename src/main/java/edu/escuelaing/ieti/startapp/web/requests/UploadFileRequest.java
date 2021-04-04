package edu.escuelaing.ieti.startapp.web.requests;

public class UploadFileRequest {
    private String bucketName;
    private String fileName;

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public UploadFileRequest() {
    }

    public UploadFileRequest(String bucketName, String fileName) {
        this.bucketName = bucketName;
        this.fileName = fileName;
    }
}
