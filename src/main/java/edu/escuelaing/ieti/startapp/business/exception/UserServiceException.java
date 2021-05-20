package edu.escuelaing.ieti.startapp.business.exception;

public class UserServiceException extends Exception {

	private static final long serialVersionUID = 1L;
	public static final String ROLE_INCORRECT_EXCEPTION = "Role incorrect";
	public static final String USER_NOT_FOUND_EXCEPTION = "User not found";
	public static final String USER_REPEATED_EXCEPTION = "User repeated";
	public static final String USER_ROLE_NOT_FOUND_EXCEPTION = "We did not find the users with this role";
    public UserServiceException(String message){
        super(message);
    }

}
