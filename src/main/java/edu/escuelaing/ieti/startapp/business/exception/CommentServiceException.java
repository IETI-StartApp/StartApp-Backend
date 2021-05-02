package edu.escuelaing.ieti.startapp.business.exception;

public class CommentServiceException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public static final String PROJECT_ID_NOT_FOUND_EXCEPTION = "We did not find the project with this id";
    public CommentServiceException(String message){
        super(message);
    }

}