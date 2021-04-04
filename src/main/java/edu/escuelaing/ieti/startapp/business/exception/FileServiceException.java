package edu.escuelaing.ieti.startapp.business.exception;

public class FileServiceException extends Exception {
    public static final String INVALID_EXTENSION = "Extensión de archivo inválida";
    public static final String UPLOAD_ERROR = "Error al subir el archivo";

    public FileServiceException(String message){
        super(message);
    }
}
