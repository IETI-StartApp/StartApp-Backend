package edu.escuelaing.ieti.startapp.business;

import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import edu.escuelaing.ieti.startapp.business.exception.ChatServiceException;
import edu.escuelaing.ieti.startapp.business.exception.UserServiceException;
import edu.escuelaing.ieti.startapp.business.model.Chat;
import edu.escuelaing.ieti.startapp.business.model.Finance;
import edu.escuelaing.ieti.startapp.business.model.Message;
import edu.escuelaing.ieti.startapp.business.model.Project;
import edu.escuelaing.ieti.startapp.business.model.User;
import edu.escuelaing.ieti.startapp.business.model.enums.UserRole;
import edu.escuelaing.ieti.startapp.business.repositories.ChatRepository;
import edu.escuelaing.ieti.startapp.business.services.projectservices.ChatServices;
import edu.escuelaing.ieti.startapp.business.services.projectservices.impl.ChatServicesImpl;

@SpringBootTest
@AutoConfigureMockMvc
class ChatServiceTests {
	private ChatRepository chatRepositoryMock = Mockito.mock(ChatRepository.class);
	private ChatServices chatServices = new ChatServicesImpl(chatRepositoryMock);
	private User user1, user2, userBad;
	private List<User> users;
	private Project testProject1;
	private Message message1, message2;
	private List<Message> messages;
	private List<Chat> chats;
	private Chat chat;

	@BeforeEach
	void setUp() {
		initializeChat();

	}

	@Test
	void shouldCreateChat() {
		when(chatRepositoryMock.save(Mockito.any())).thenReturn(chat);
		Chat chatTest = chatServices.createChat(chat);
		Assertions.assertEquals(chatTest, chat);
	}
	@Test
	void shouldFindAllChatsByUser() {
		try {
			User userTest = new User("test", "test", "test@gmail.com", 1111111111, UserRole.CONSULTOR, "This is a test", null);
			List<Chat> ChatsTest = chatServices.findAllChatsByUser(userTest);
			Assertions.fail();
		} catch (UserServiceException | ChatServiceException e) {
			Assertions.assertEquals(ChatServiceException.CHAT_ROL_NOT_FOUND,e.getMessage()); 
		}
	}
	@Test
	void shouldFindAllChatsByUserInvestor() {
		when(chatRepositoryMock.findByInvestor(Mockito.any())).thenReturn(Optional.of(chats));
		try {
			List<Chat> chatsTest = chatServices.findAllChatsByUser(user1);
			Assertions.assertEquals(chats, chatsTest);
		} catch (UserServiceException | ChatServiceException e) {
			Assertions.fail();
		}
	}

	@Test
	void shouldFindAllChatsByUserEntrepreneur() {
		when(chatRepositoryMock.findByEntrepreneur(Mockito.any())).thenReturn(Optional.of(chats));
		try {
			List<Chat> chatsTest = chatServices.findAllChatsByUser(user2);
			Assertions.assertEquals(chats, chatsTest);
		} catch (UserServiceException | ChatServiceException e) {
			Assertions.fail();
		}
	}

	@Test
	void shouldNotFindAllChatsByUserInvestor() {
		when(chatRepositoryMock.findByInvestor(Mockito.any())).thenReturn(Optional.empty());
		try {
			List<Chat> chatsTest = chatServices.findAllChatsByUser(user1);
			Assertions.fail();
		} catch (UserServiceException | ChatServiceException e) {
			Assertions.assertEquals(UserServiceException.ROLE_INCORRECT_EXCEPTION, e.getMessage());
		}
	}

	@Test
	void shouldNotFindAllChatsByUserEntrepreneur() {
		when(chatRepositoryMock.findByEntrepreneur(Mockito.any())).thenReturn(Optional.empty());
		try {
			List<Chat> chatsTest = chatServices.findAllChatsByUser(user2);
			Assertions.fail();
		} catch (UserServiceException | ChatServiceException e) {
			Assertions.assertEquals(UserServiceException.ROLE_INCORRECT_EXCEPTION ,e.getMessage());
		}
	}

	@Test
	void shouldFindAllMessagesByChat() {
		when(chatRepositoryMock.findById(Mockito.any())).thenReturn(Optional.of(chat));
		try {
			List<Message> messagesTest = chatServices.findAllMessagesByChat("test");
			Assertions.assertEquals(messagesTest, messages);
		} catch (ChatServiceException e) {
			Assertions.fail();
		}

	}

	@Test
	void shouldNotFindAllMessagesByChat() {
		when(chatRepositoryMock.findById(Mockito.any())).thenReturn(Optional.empty());
		try {
			List<Message> messagesTest = chatServices.findAllMessagesByChat("fail");
			Assertions.fail();
		} catch (ChatServiceException e) {
			Assertions.assertEquals(ChatServiceException.CHAT_NOT_FOUND, e.getMessage());
		}
	}

	@Test
	void shouldAddMessage() {
		when(chatRepositoryMock.save(Mockito.any())).thenReturn(chat);
		Message messageTest = chatServices.addMessage(user1, chat, "Hola");
		Assertions.assertEquals("Hola", messageTest.getTextmessage());
	}

	@Test
	void shouldFindChatById() {
		when(chatRepositoryMock.findById(Mockito.any())).thenReturn(Optional.of(chat));
		try {
			Chat chatTest = chatServices.findChatById("test");
			Assertions.assertEquals(chatTest, chat);
		} catch (ChatServiceException e) {
			Assertions.fail();
		}
	}

	@Test
	void shouldNotFindChatById() {
		when(chatRepositoryMock.findById(Mockito.any())).thenReturn(Optional.empty());
		try {
			Chat chatTest = chatServices.findChatById("test");
			Assertions.fail();
		} catch (ChatServiceException e) {
			Assertions.assertEquals(ChatServiceException.CHAT_NOT_FOUND, e.getMessage());
		}
	}

	private void initializeChat() {
		users = new ArrayList<User>();
		messages = new ArrayList<Message>();
		chats = new ArrayList<Chat>();
		List<Project> projects = new ArrayList<Project>();
		Finance testFinance1 = new Finance(1L, 2, 1L, 2L, new Date(), new Date());
		testProject1 = new Project("testProject", "abc.com", "abc.com", "CO", "testDesc", testFinance1);
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
	}

}
