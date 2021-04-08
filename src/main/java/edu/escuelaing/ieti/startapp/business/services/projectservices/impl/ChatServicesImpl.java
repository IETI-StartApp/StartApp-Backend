package edu.escuelaing.ieti.startapp.business.services.projectservices.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.escuelaing.ieti.startapp.business.exception.ChatServiceException;
import edu.escuelaing.ieti.startapp.business.exception.UserServiceException;
import edu.escuelaing.ieti.startapp.business.model.Chat;
import edu.escuelaing.ieti.startapp.business.model.Message;
import edu.escuelaing.ieti.startapp.business.model.User;
import edu.escuelaing.ieti.startapp.business.repositories.ChatRepository;
import edu.escuelaing.ieti.startapp.business.services.projectservices.ChatServices;

@Service
public class ChatServicesImpl implements ChatServices {

	private final ChatRepository chatRepository;

	@Autowired
	public ChatServicesImpl(ChatRepository chatRepository) {
		this.chatRepository = chatRepository;
	}

	@Override
	public Chat createChat(Chat chat) {
		chatRepository.save(chat);
		return chat;
	}


	@Override
	public List<Chat> findAllChatsByUser(User user) throws UserServiceException, ChatServiceException {
		List<Chat> chats;
		switch (user.getRole()) {
		case INVESTOR:
		case INVESTORPREMIUM:
			chats = chatRepository.findByInvestor(user)
			.orElseThrow(() -> new UserServiceException(UserServiceException.ROLE_INCORRECT_EXCEPTION));
			break;
		case ENTREPRENEUR:
		case ENTREPRENEURPREMIUM:
			chats = chatRepository.findByEntrepreneur(user)
			.orElseThrow(() -> new UserServiceException(UserServiceException.ROLE_INCORRECT_EXCEPTION));
			break;
		default:
			throw new ChatServiceException(ChatServiceException.CHAT_ROL_NOT_FOUND);
		}
		return chats;
	}

	@Override
	public List<Message> findAllMessagesByChat(String idChat) throws ChatServiceException {
		return chatRepository.findById(idChat)
				.orElseThrow(() -> new ChatServiceException(ChatServiceException.CHAT_NOT_FOUND)).getMessages();
	}

	@Override
	public Message addMessage(User user, Chat chat, String message) {
		Date date = new Date();
		SimpleDateFormat FormatDate = new SimpleDateFormat("hh: mm: ss a dd-MMM-aaaa");
		List<Message> messages = chat.getMessages();
		messages.add(new Message(message, user, FormatDate.format(date).toString()));
		chat.setMessages(messages);
		chatRepository.save(chat);
		return new Message(message, user, FormatDate.toString());
	}

	@Override
	public Chat findChatById(String idChat) throws ChatServiceException {
		return chatRepository.findById(idChat)
				.orElseThrow(() -> new ChatServiceException(ChatServiceException.CHAT_NOT_FOUND));
	}
}
