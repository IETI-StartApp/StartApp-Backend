package edu.escuelaing.ieti.startapp.business.exception;

public class ProjectServiceException extends Exception {

	private static final long serialVersionUID = 1L;
	public static final String PROJECT_NOT_FOUND_EXCEPTION = "Project not found";
    public ProjectServiceException(String message){
        super(message);
    }
}