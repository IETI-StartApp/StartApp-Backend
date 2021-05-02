package edu.escuelaing.ieti.startapp.web;

import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import edu.escuelaing.ieti.startapp.business.exception.ChatServiceException;
import edu.escuelaing.ieti.startapp.business.exception.UserServiceException;
import edu.escuelaing.ieti.startapp.business.model.Chat;
import edu.escuelaing.ieti.startapp.business.model.Comment;
import edu.escuelaing.ieti.startapp.business.model.Finance;
import edu.escuelaing.ieti.startapp.business.model.Message;
import edu.escuelaing.ieti.startapp.business.model.Project;
import edu.escuelaing.ieti.startapp.business.model.User;
import edu.escuelaing.ieti.startapp.business.model.enums.UserRole;
import edu.escuelaing.ieti.startapp.business.services.projectservices.ChatServices;
import edu.escuelaing.ieti.startapp.business.services.projectservices.UserServices;
import edu.escuelaing.ieti.startapp.business.services.projectservices.impl.UserServicesImpl;
import edu.escuelaing.ieti.startapp.web.controllers.ChatController;
import edu.escuelaing.ieti.startapp.web.requests.ChatRequest;

@SpringBootTest
@AutoConfigureMockMvc
class ChatControllerTests {
	private ChatServices chatServicesMock = Mockito.mock(ChatServices.class);
	private UserServices userServicesMock = Mockito.mock(UserServicesImpl.class);
	private ChatController chatController = new ChatController(chatServicesMock, userServicesMock);
	private BindingResult result;
	private User user1, user2, userBad;
	private List<User> users;
	private Project testProject1;
	private Message message1, message2;
	private List<Message> messages;
	private List<Chat> chats;
	private Chat chat;
	private List<Comment> comments;

	@BeforeEach
	void setUp() {
		initializeChat();
		result = Mockito.mock(BindingResult.class);
	}

	@Test
	void shouldCreateChat() {
		when(chatServicesMock.createChat(Mockito.any())).thenReturn(chat);
		ResponseEntity<Object> httpResponse = chatController.createChat(new ChatRequest(chat), result);
		Assertions.assertEquals(HttpStatus.CREATED, httpResponse.getStatusCode());
	}

	@Test
	void shouldFindAllChatByUser() {
		try {
			when(chatServicesMock.findAllChatsByUser(Mockito.any())).thenReturn(chats);
			ResponseEntity<Object> httpResponse = chatController.findAllChatByUser("test");
			Assertions.assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
			Assertions.assertEquals(chats, httpResponse.getBody());
		} catch (UserServiceException | ChatServiceException e) {
			Assertions.fail();
		}
	}

	@Test
	void shouldNotFindAllChatByUser() {
		try {
			when(chatServicesMock.findAllChatsByUser(Mockito.any()))
					.thenThrow(new ChatServiceException(ChatServiceException.CHAT_ROL_NOT_FOUND));
			ResponseEntity<Object> httpResponse = chatController.findAllChatByUser("fail");
			Assertions.assertEquals(HttpStatus.NOT_FOUND, httpResponse.getStatusCode());
			Map<String, String> error = new HashMap<>();
			error.put("Error", ChatServiceException.CHAT_ROL_NOT_FOUND);
			Assertions.assertEquals(error, httpResponse.getBody());
		} catch (UserServiceException | ChatServiceException e) {
			Assertions.fail();
		}
	}

	@Test
	void shouldAddMessage() {
		when(chatServicesMock.addMessage(Mockito.any(), Mockito.any(), Mockito.anyString())).thenReturn(message1);
		ResponseEntity<Object> httpResponse = chatController.addMessage("test", "test", "Hola");
		Assertions.assertEquals(HttpStatus.ACCEPTED, httpResponse.getStatusCode());
		Assertions.assertEquals(message1, httpResponse.getBody());
	}

	@Test
	void shouldFindChatById() {
		try {
			when(chatServicesMock.findChatById(Mockito.anyString())).thenReturn(chat);
			ResponseEntity<Object> httpResponse = chatController.findChatById("test");
			Assertions.assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
			Assertions.assertEquals(chat, httpResponse.getBody());
		} catch (ChatServiceException e) {
			Assertions.fail();
		}
	}

	@Test
	void shouldNotFindChatById() {
		try {
			when(chatServicesMock.findChatById(Mockito.anyString()))
					.thenThrow(new ChatServiceException(ChatServiceException.CHAT_NOT_FOUND));
			ResponseEntity<Object> httpResponse = chatController.findChatById("test");
			Assertions.assertEquals(HttpStatus.NOT_FOUND, httpResponse.getStatusCode());
			Map<String, String> error = new HashMap<>();
			error.put("Error", ChatServiceException.CHAT_ROL_NOT_FOUND);
		} catch (ChatServiceException e) {
			Assertions.fail();
		}
	}

	@Test
	void shouldFindAllMessagesById() {
		try {
			when(chatServicesMock.findAllMessagesByChat(Mockito.anyString())).thenReturn(messages);
			ResponseEntity<Object> httpResponse = chatController.findAllMessagesById("test");
			Assertions.assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
			Assertions.assertEquals(messages, httpResponse.getBody());
		} catch (ChatServiceException e) {
			Assertions.fail();
		}
	}

	@Test
	void shouldNotFindAllMessagesById() {
		try {
			when(chatServicesMock.findAllMessagesByChat(Mockito.anyString()))
					.thenThrow(new ChatServiceException(ChatServiceException.CHAT_NOT_FOUND));
			ResponseEntity<Object> httpResponse = chatController.findAllMessagesById("fail");
			Assertions.assertEquals(HttpStatus.NOT_FOUND, httpResponse.getStatusCode());
			Map<String, String> error = new HashMap<>();
			error.put("Error", ChatServiceException.CHAT_ROL_NOT_FOUND);
		} catch (ChatServiceException e) {
			Assertions.fail();
		}
	}

	private void updateChat() {
		chat.getId();
		chat.setEntrepreneur(chat.getEntrepreneur());
		chat.setInvestor(chat.getInvestor());
		chat.setMessages(chat.getMessages());
		message1.setUser(message1.getUser());
		message1.setDate(message1.getDate());
		message1.setTextmessage(message1.getTextmessage());
	}

	private void initializeChat() {
		users = new ArrayList<User>();
		messages = new ArrayList<Message>();
		chats = new ArrayList<Chat>();
		List<Project> projects = new ArrayList<Project>();
		Finance testFinance1 = new Finance(1L, 2, 1L, 2L, new Date(), new Date());
		testProject1 = new Project("testProject", "abc.com", "abc.com", "CO", "testDesc", testFinance1, comments);
		testProject1.setId("test");
		projects.add(testProject1);
		user1 = new User("test", "test", "test@gmail.com", 1111111111, UserRole.INVESTOR, "This is a test", projects);
		user2 = new User("test", "test", "test@gmail.com", 1111111112, UserRole.ENTREPRENEUR, "This is a test",
				projects);
		userBad = new User("test", "test", "test", -1111111112, UserRole.INVESTOR, "This is a test", projects);
		user1.setId("test");
		users.add(user1);
		users.add(user2);
		Date date = new Date();
		SimpleDateFormat FormatDate = new SimpleDateFormat("hh: mm: ss a dd-MMM-aaaa");
		message1 = new Message("Hola", user1, FormatDate.format(date).toString());
		message2 = new Message("adios", user2, FormatDate.format(date).toString());
		messages.add(message1);
		messages.add(message2);
		chat = new Chat(user1, user2, messages);
		chat.setId("test");
		chats.add(chat);
		updateChat();
	}
}
