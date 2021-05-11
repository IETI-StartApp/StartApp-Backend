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

import edu.escuelaing.ieti.startapp.business.model.User;
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
import edu.escuelaing.ieti.startapp.business.model.Comment;
import edu.escuelaing.ieti.startapp.business.model.Finance;
import edu.escuelaing.ieti.startapp.business.model.Project;
import edu.escuelaing.ieti.startapp.business.services.projectservices.IProjectServices;
import edu.escuelaing.ieti.startapp.business.services.projectservices.impl.ProjectServicesImpl;
import edu.escuelaing.ieti.startapp.web.controllers.ProjectController;
import edu.escuelaing.ieti.startapp.web.handlers.Error;
import edu.escuelaing.ieti.startapp.web.requests.ProjectRequest;

@SpringBootTest
@AutoConfigureMockMvc

class ProjectControllerTests {

    private IProjectServices projectServicesMock = Mockito.mock(ProjectServicesImpl.class);
    private ProjectController projectController = new ProjectController(projectServicesMock);
    private BindingResult result;
    private Project testProject1,testProject2;
	private List<Project> projects;
	private List<Comment> comments;

    @BeforeEach
    void setUp(){
        initializeProjects();
        result = Mockito.mock(BindingResult.class);
    }

    @Test
    void shouldNotCreateProject() {
        when(projectServicesMock.createProject(Mockito.any())).thenReturn(testProject1);
        List<Error> errorList = new LinkedList<>();
        initializeErrors(errorList);
        setUpMockErrors(errorList);
        Assertions.assertNotEquals(0, errorList.get(0).hashCode());
        ResponseEntity<Object> httpResponse = projectController.createProject(new ProjectRequest(testProject1),result);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST,httpResponse.getStatusCode());
        List<Error> errors = ((Map<String,List<Error>>) Objects.requireNonNull(httpResponse.getBody())).get("errors");
        Assertions.assertArrayEquals(errors.toArray(),errorList.toArray());
    }

    @Test
    void shouldCreateProject(){
        when(projectServicesMock.createProject(Mockito.any())).thenReturn(testProject2);
        when(result.hasErrors()).thenReturn(false);
        ResponseEntity<Object> httpResponse = projectController.createProject(new ProjectRequest(testProject2),result);
        Assertions.assertEquals(HttpStatus.CREATED,httpResponse.getStatusCode());
    }
    @Test
	void shouldGetAllProjects() {
		when(projectServicesMock.getAllProjects()).thenReturn(projects);
		ResponseEntity<Object> httpResponse = projectController.getAllProyects();
		Assertions.assertEquals(HttpStatus.OK,httpResponse.getStatusCode());
		Assertions.assertEquals(projects, httpResponse.getBody());
	}
    @Test
	void shouldGetProjectById() {
    	try {
			when(projectServicesMock.getProyectById(Mockito.anyString())).thenReturn(testProject2);
			ResponseEntity<Object> httpResponse = projectController.getProyectById(testProject2.getId());
	    	Assertions.assertEquals(HttpStatus.OK,httpResponse.getStatusCode());
	    	Assertions.assertEquals(testProject2, httpResponse.getBody());
		} catch (ProjectServiceException e) {
			Assertions.fail();
		}
    }
	@Test
    void shouldNotGetProjectById() {
			try {
				when(projectServicesMock.getProyectById(Mockito.anyString())).thenThrow(new ProjectServiceException(ProjectServiceException.PROJECT_NOT_FOUND_EXCEPTION));
			} catch (ProjectServiceException e) {
				Assertions.fail();
			}
			ResponseEntity<Object> httpResponse = projectController.getProyectById("fail");
			Assertions.assertEquals(HttpStatus.NOT_FOUND, httpResponse.getStatusCode());
			Map<String, String> error = (HashMap<String, String>) httpResponse.getBody();
			Assertions.assertEquals(ProjectServiceException.PROJECT_NOT_FOUND_EXCEPTION, error.get("Error"));
    }

    private void initializeErrors(List<Error> errors){
        errors.add(new Error("finance.minimumInvestment","La inversión mínima debe ser mayor a 100000"));
        errors.add(new Error("description","La descripción del proyecto debe tener míninimo 20 carácteres y máximo 250"));
    }

    private void setUpMockErrors(List<Error> errors){
        List<FieldError> fieldErrors = errors.stream()
                .map(error -> new FieldError("test",error.getField(),error.getMessage()))
                .collect(Collectors.toList());
        when(result.hasErrors()).thenReturn(true);
        when(result.getFieldErrors()).thenReturn(fieldErrors);
    }

    private void initializeProjects(){
    	projects = new ArrayList<Project>();
        Finance testFinance1 = new Finance(1L,2,1L,2L,new Date(),new Date());
        testProject1 = new Project("testProject", "abc.com", "abc.com", "CO",
                "testDesc",testFinance1, comments, new User());
        testProject1.setId("id123");
        Finance testFinance2 = new Finance(1L,2,1L,200000,new Date(),new Date());
        testProject2 = new Project("testProject", "abc.com", "abc.com", "CO",
                "A large description to test",testFinance2, comments, new User());
        testProject2.setId("id1234");
		projects.add(testProject1);
		projects.add(testProject2);
    }


}
