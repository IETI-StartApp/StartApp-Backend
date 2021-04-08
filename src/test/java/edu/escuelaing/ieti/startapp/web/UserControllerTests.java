package edu.escuelaing.ieti.startapp.web;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import edu.escuelaing.ieti.startapp.business.exception.ProjectServiceException;
import edu.escuelaing.ieti.startapp.business.exception.UserServiceException;
import edu.escuelaing.ieti.startapp.business.model.Finance;
import edu.escuelaing.ieti.startapp.business.model.Project;
import edu.escuelaing.ieti.startapp.business.model.User;
import edu.escuelaing.ieti.startapp.business.model.enums.UserRole;
import edu.escuelaing.ieti.startapp.business.services.projectservices.IProjectServices;
import edu.escuelaing.ieti.startapp.business.services.projectservices.UserServices;
import edu.escuelaing.ieti.startapp.business.services.projectservices.impl.ProjectServicesImpl;
import edu.escuelaing.ieti.startapp.business.services.projectservices.impl.UserServicesImpl;
import edu.escuelaing.ieti.startapp.web.controllers.UserController;
import edu.escuelaing.ieti.startapp.web.handlers.Error;
import edu.escuelaing.ieti.startapp.web.requests.UserRequest;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTests {

	private UserServices userServicesMock = Mockito.mock(UserServicesImpl.class);
	private IProjectServices projectServicesMock = Mockito.mock(ProjectServicesImpl.class);
	private UserController userController = new UserController(userServicesMock, projectServicesMock);
	private BindingResult result;
	private User user1, user2, userBad;
	private Project testProject1;
	private List<User> users;

	@BeforeEach
	void setUp() {
		initializeUser();
		result = Mockito.mock(BindingResult.class);
	}

	@Test
	void shouldNotCreateUser() {
		try {
			when(userServicesMock.createUser(Mockito.any())).thenReturn(userBad);
			List<Error> errorsList = new LinkedList<>();
			initializeErrors(errorsList);
			setUpMockErrors(errorsList);
			Assertions.assertNotEquals(0, errorsList.get(0).hashCode());
			ResponseEntity<Object> httpResponse = userController.createUser(new UserRequest(userBad), result);
			Assertions.assertEquals(HttpStatus.BAD_REQUEST, httpResponse.getStatusCode());
			List<Error> errors = ((Map<String, List<Error>>) Objects.requireNonNull(httpResponse.getBody()))
					.get("errors");
			Assertions.assertArrayEquals(errors.toArray(), errorsList.toArray());
		} catch (UserServiceException e) {
			Assertions.fail();
		}

	}

	@Test
	void shouldCreateUser() {
		try {
			when(userServicesMock.createUser(Mockito.any())).thenReturn(user1);
			ResponseEntity<Object> httpResponse = userController.createUser(new UserRequest(user1), result);
			Assertions.assertEquals(HttpStatus.CREATED, httpResponse.getStatusCode());
		} catch (UserServiceException e) {
			Assertions.fail();
		}
	}

	@Test
	void shouldNotCreateUserRepeated() {
		try {
			when(userServicesMock.createUser(Mockito.any()))
					.thenThrow(new UserServiceException(UserServiceException.USER_REPEATED_EXCEPTION));
			ResponseEntity<Object> httpResponse = userController.createUser(new UserRequest(user1), result);
			Assertions.assertEquals(HttpStatus.NOT_FOUND, httpResponse.getStatusCode());
			Map<String, String> error = new HashMap<>();
			error.put("Error", UserServiceException.USER_REPEATED_EXCEPTION);
			Assertions.assertEquals(error, httpResponse.getBody());
		} catch (UserServiceException e) {
			Assertions.fail();
		}

	}

	@Test
	void shouldGetUserById() {
		try {
			when(userServicesMock.getUserById(Mockito.anyString())).thenReturn(user1);
			ResponseEntity<Object> httpResponse = userController.getUserById("test");
			Assertions.assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
			Assertions.assertEquals(user1, httpResponse.getBody());
		} catch (UserServiceException e) {
			Assertions.fail();
		}
	}

	@Test
	void shouldNotGetUserById() {
		try {
			when(userServicesMock.getUserById(Mockito.anyString()))
					.thenThrow(new UserServiceException(UserServiceException.USER_NOT_FOUND_EXCEPTION));
			ResponseEntity<Object> httpResponse = userController.getUserById("fail");
			Assertions.assertEquals(HttpStatus.NOT_FOUND, httpResponse.getStatusCode());
			Map<String, String> error = new HashMap<>();
			error.put("Error", UserServiceException.USER_NOT_FOUND_EXCEPTION);
			Assertions.assertEquals(error, httpResponse.getBody());
		} catch (UserServiceException e) {
			Assertions.fail();
		}
	}

	@Test
	void shouldGetUserByIdentificationAndRole() {
		try {
			when(userServicesMock.getUserByIdentificationAndRole(Mockito.anyLong(), Mockito.anyString()))
					.thenReturn(user1);
			ResponseEntity<Object> httpResponse = userController.getUserByIdentificationAndRole(1111111111, "INVESTOR");
			Assertions.assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
		} catch (UserServiceException e) {
			Assertions.fail();
		}
	}

	@Test
	void shouldNotGetUserByIdentificationAndRole() {
		try {
			when(userServicesMock.getUserByIdentificationAndRole(Mockito.anyLong(), Mockito.anyString()))
					.thenThrow(new UserServiceException(UserServiceException.USER_NOT_FOUND_EXCEPTION));
			ResponseEntity<Object> httpResponse = userController.getUserByIdentificationAndRole(111511111, "INVESTOR");
			Map<String, String> error = new HashMap<>();
			error.put("Error", UserServiceException.USER_NOT_FOUND_EXCEPTION);
			Assertions.assertEquals(error, httpResponse.getBody());
		} catch (UserServiceException e) {
			Assertions.fail();
		}
	}

	@Test
	void shouldGetUserByRole() {
		try {
			when(userServicesMock.getUserByRole(Mockito.anyString())).thenReturn(users);
			ResponseEntity<Object> httpResponse = userController.getUserByRole("INVESTOR");
			Assertions.assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
			Assertions.assertEquals(httpResponse.getBody(), users);
		} catch (UserServiceException e) {
			Assertions.fail();
		}
	}

	@Test
	void shouldNotGetUserByRole() {
		try {
			when(userServicesMock.getUserByRole(Mockito.anyString()))
					.thenThrow(new UserServiceException(UserServiceException.USER_ROLE_NOT_FOUND_EXCEPTION));
			ResponseEntity<Object> httpResponse = userController.getUserByRole("CONSULTOR");
			Assertions.assertEquals(HttpStatus.NOT_FOUND, httpResponse.getStatusCode());
			Map<String, String> error = new HashMap<>();
			error.put("Error", UserServiceException.USER_ROLE_NOT_FOUND_EXCEPTION);
			Assertions.assertEquals(error, httpResponse.getBody());
		} catch (UserServiceException e) {
			Assertions.fail();
		}
	}

	@Test
	void shouldAddProjectToUser() {
		when(userServicesMock.addProject(Mockito.any(), Mockito.any())).thenReturn(user1);
		ResponseEntity<Object> httpResponse = userController.addProjectToUser("test", "test");
		Assertions.assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
		Assertions.assertEquals(user1, httpResponse.getBody());
	}

	@Test
	void shoulNotdAddProjectToUser() {
		try {
			when(userServicesMock.getUserById(Mockito.anyString()))
					.thenThrow(new UserServiceException(UserServiceException.USER_NOT_FOUND_EXCEPTION));
			ResponseEntity<Object> httpResponse = userController.addProjectToUser("fail", "fail");
			Assertions.assertEquals(HttpStatus.NOT_FOUND, httpResponse.getStatusCode());
			Map<String, String> error = new HashMap<>();
			error.put("Error", UserServiceException.USER_NOT_FOUND_EXCEPTION);
			Assertions.assertEquals(error, httpResponse.getBody());
		} catch (UserServiceException e) {
			Assertions.fail();
		}
		
	}

	private void initializeErrors(List<Error> errors) {
		errors.add(new Error("firstName", "El nombre del usuario debe tener maximo 4 caracteres y minimo 30"));
		errors.add(new Error("lastName", "El nombre del usuario debe tener maximo 4 caracteres y minimo 30"));
		errors.add(new Error("email", "debe ser una direccion de correo electronico con formato correcto"));
		errors.add(new Error("identification", "La cedula es incorrecta"));
	}

	private void setUpMockErrors(List<Error> errors) {
		List<FieldError> fieldErrors = errors.stream()
				.map(error -> new FieldError("test", error.getField(), error.getMessage()))
				.collect(Collectors.toList());
		when(result.hasErrors()).thenReturn(true);
		when(result.getFieldErrors()).thenReturn(fieldErrors);
	}

	private void initializeUser() {
		users = new ArrayList<User>();
		List<Project> projects = new ArrayList<Project>();
		Finance testFinance1 = new Finance(1L, 2, 1L, 2L, new Date(), new Date());
		testProject1 = new Project("testProject", "abc.com", "abc.com", "CO", "testDesc", testFinance1);
		testProject1.setId("test");
		projects.add(testProject1);
		user1 = new User("test", "test", "test@gmail.com", 1111111111, UserRole.INVESTOR, "This is a test", projects);
		user2 = new User("test", "test", "test@gmail.com", 1111111112, UserRole.INVESTOR, "This is a test", projects);
		userBad = new User("test", "test", "test", -1111111112, UserRole.INVESTOR, "This is a test", projects);
		user1.setId("test");
		users.add(user1);
		users.add(user2);
	}
}
