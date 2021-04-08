package edu.escuelaing.ieti.startapp.business.services.projectservices;

import edu.escuelaing.ieti.startapp.business.exception.ChatServiceException;
import edu.escuelaing.ieti.startapp.business.exception.UserServiceException;
import edu.escuelaing.ieti.startapp.business.model.Chat;
import edu.escuelaing.ieti.startapp.business.model.Message;
import edu.escuelaing.ieti.startapp.business.model.User;

import java.util.List;

public interface ChatServices {

	Chat createChat(Chat chat);

	Chat findChatById(String idChat) throws ChatServiceException;

	List<Message> findAllMessagesByChat(String idChat) throws ChatServiceException;

	Message addMessage(User user, Chat chat, String message);

	List<Chat> findAllChatsByUser(User user) throws UserServiceException, ChatServiceException;
}
