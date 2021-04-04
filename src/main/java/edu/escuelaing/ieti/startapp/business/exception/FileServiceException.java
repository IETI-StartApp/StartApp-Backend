package edu.escuelaing.ieti.startapp.business.exception;

public class FileServiceException extends Exception {
    public static final String INVALID_IMAGE = "Extensión de imagen inválida";
    public static final String UPLOAD_ERROR = "Error al subir el archivo";

    public FileServiceException(String message){
        super(message);
    }
}
