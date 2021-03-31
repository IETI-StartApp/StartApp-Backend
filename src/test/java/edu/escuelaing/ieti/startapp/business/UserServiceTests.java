package edu.escuelaing.ieti.startapp.business;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import edu.escuelaing.ieti.startapp.business.exception.UserServiceException;
import edu.escuelaing.ieti.startapp.business.model.Project;
import edu.escuelaing.ieti.startapp.business.model.User;
import edu.escuelaing.ieti.startapp.business.model.enums.UserRole;
import edu.escuelaing.ieti.startapp.business.repositories.UserRepository;
import edu.escuelaing.ieti.startapp.business.services.projectservices.UserServices;
import edu.escuelaing.ieti.startapp.business.services.projectservices.impl.UserServicesImpl;

@SpringBootTest
@AutoConfigureMockMvc
class UserServiceTests {
	private UserRepository userRepositoryMock = Mockito.mock(UserRepository.class);
	private UserServices userServices = new UserServicesImpl(userRepositoryMock);
	private User user1, user2;
	private List<User> users;

	@BeforeEach
	void setUp() {
		setUpProjects();
	}

	@Test
	void shouldSaveUser() {
		when(userRepositoryMock.save(Mockito.any())).thenReturn(user1);
		try {
			User user = userServices.createUser(user1);
			Assertions.assertEquals(user1, user);
		} catch (UserServiceException e) {
			Assertions.fail();
		}
	}
	@Test
	void shouldGetUserById() {
		when(userRepositoryMock.findById(Mockito.any())).thenReturn(Optional.of(user1));
		try {
			User user = userServices.getUserById("test");
			Assertions.assertEquals(user1, user);
		} catch (UserServiceException e) {
			Assertions.fail();
		}
	}
	@Test
	void shouldNotGetUserById() {
		when(userRepositoryMock.findById(Mockito.any())).thenReturn(Optional.empty());
		try {
			User user = userServices.getUserById("fail");
			Assertions.fail();
		} catch (UserServiceException e) {
			Assertions.assertEquals(UserServiceException.USER_NOT_FOUND_EXCEPTION, e.getMessage());
		}
	}
	@Test
	void shouldGetUserByIdentificationAndRole() {
		when(userRepositoryMock.findByIdentificationAndRole(Mockito.anyLong(), Mockito.anyString())).thenReturn(Optional.of(user1));
		try {
			User user = userServices.getUserByIdentificationAndRole(user1.getIdentification(), "INVESTOR");
			Assertions.assertEquals(user1, user);
		} catch (UserServiceException e) {
			Assertions.fail();
		}
	}
	@Test
	void shouldNotGetUserByIdentificationAndRole() {
		when(userRepositoryMock.findByIdentificationAndRole(Mockito.anyLong(), Mockito.anyString())).thenReturn(Optional.empty());
		try {
			User user = userServices.getUserByIdentificationAndRole(user1.getIdentification(), "INVEST");
			Assertions.fail();
		} catch (UserServiceException e) {
			Assertions.assertEquals(UserServiceException.USER_NOT_FOUND_EXCEPTION, e.getMessage());
		}
	}
	@Test
	void shouldGetUserByRole() {
		when(userRepositoryMock.findByRole(Mockito.anyString())).thenReturn(Optional.of(users));
		try {
			List<User> user = userServices.getUserByRole("INVESTOR");
			Assertions.assertEquals(users, user);
		} catch (UserServiceException e) {
			Assertions.fail();
		}
	}
	@Test
	void shouldNotGetUserByRole() {
		when(userRepositoryMock.findByRole(Mockito.anyString())).thenReturn(Optional.empty());
		try {
			List<User> user = userServices.getUserByRole("INVES");
			Assertions.fail();
		} catch (UserServiceException e) {
			Assertions.assertEquals(UserServiceException.USER_ROLE_NOT_FOUND_EXCEPTION, e.getMessage());
		}
	}
	private void setUpProjects() {
		users = new ArrayList<User>();
		List<Project> projects = new ArrayList<Project>();
		
		user1 = new User("test", "test", "test@gmail.com", 1111111111, UserRole.INVESTOR, "This is a test", projects);
		user2 = new User("test", "test", "test@gmail.com", 1111111112, UserRole.INVESTOR, "This is a test", projects);
		user1.setId("test");
		users.add(user1);
		users.add(user2);
	}
}
