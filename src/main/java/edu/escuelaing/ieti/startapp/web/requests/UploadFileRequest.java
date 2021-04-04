package edu.escuelaing.ieti.startapp.web.requests;

public class UploadFileRequest {

    private String folder;
    private String filename;
    private String type;

    public UploadFileRequest() {
    }

    public UploadFileRequest(String folder, String filename, String type) {
        this.folder = folder;
        this.filename = filename;
        this.type = type;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
