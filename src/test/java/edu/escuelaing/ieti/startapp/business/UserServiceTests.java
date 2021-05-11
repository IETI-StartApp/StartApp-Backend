package edu.escuelaing.ieti.startapp.business;

import static org.mockito.Mockito.when;

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

import edu.escuelaing.ieti.startapp.business.exception.UserServiceException;
import edu.escuelaing.ieti.startapp.business.model.Comment;
import edu.escuelaing.ieti.startapp.business.model.Finance;
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
	private Project testProject1;
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
			Assertions.assertEquals(user1.getId(), user.getId());
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
		when(userRepositoryMock.findByIdentificationAndRole(Mockito.anyLong(), Mockito.anyString()))
				.thenReturn(Optional.of(user1));
		try {
			User user = userServices.getUserByIdentificationAndRole(user1.getIdentification(), "INVESTOR");
			Assertions.assertEquals(user1, user);
		} catch (UserServiceException e) {
			Assertions.fail();
		}
	}

	@Test
	void shouldNotGetUserByIdentificationAndRole() {
		when(userRepositoryMock.findByIdentificationAndRole(Mockito.anyLong(), Mockito.anyString()))
				.thenReturn(Optional.empty());
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

	@Test
	void shouldAddProjectToUser() {
		when(userRepositoryMock.save(Mockito.any())).thenReturn(user1);
		User user = userServices.addProject(user1, testProject1);
		Assertions.assertEquals(user, user1);
	}

	private void setUpProjects() {
		users = new ArrayList<User>();
		List<Project> projects = new ArrayList<Project>();
		List<Comment> comments = new ArrayList<Comment>();
		Finance testFinance1 = new Finance(1L, 2, 1L, 2L, new Date(), new Date());
		testProject1 = new Project("testProject", "abc.com", "abc.com", "CO", "testDesc", testFinance1, comments,new User());
		testProject1.setId("test");
		projects.add(testProject1);
		user1 = new User("test", "test", "test@gmail.com", 1111111111, UserRole.INVESTOR, "This is a test", projects, "test");
		user2 = new User("test", "test", "test@gmail.com", 1111111112, UserRole.INVESTOR, "This is a test", projects, "test");
		user2.setFirstName(user2.getFirstName());
		user2.setLastName(user2.getLastName());
		user2.setEmail(user2.getEmail());
		user2.setIdentification(user2.getIdentification());
		user2.setRole(user2.getRole());
		user2.setDescription(user2.getDescription());
		user2.setProjects(user2.getProjects());

		user1.setId("test");
		users.add(user1);
		users.add(user2);
	}
}
