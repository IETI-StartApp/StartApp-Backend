package edu.escuelaing.ieti.startapp.business.exception;

public class ChatServiceException extends Exception{
	private static final long serialVersionUID = 1L;
	public static final String CHAT_ROL_NOT_FOUND = "Role not found";
	public static final String CHAT_NOT_FOUND = "Chat not found";

	public ChatServiceException(String message) {
		super(message);
	}
	
	
}
