package edu.escuelaing.ieti.startapp.web;

import edu.escuelaing.ieti.startapp.business.model.Finance;
import edu.escuelaing.ieti.startapp.business.model.Project;
import edu.escuelaing.ieti.startapp.business.services.projectservices.IProjectServices;
import edu.escuelaing.ieti.startapp.business.services.projectservices.impl.ProjectServicesImpl;
import edu.escuelaing.ieti.startapp.web.controllers.ProjectController;
import edu.escuelaing.ieti.startapp.web.handlers.Error;
import edu.escuelaing.ieti.startapp.web.requests.ProjectRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.*;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc

class ProjectControllerTests {

    private IProjectServices projectServicesMock = Mockito.mock(ProjectServicesImpl.class);
    private ProjectController projectController = new ProjectController(projectServicesMock);
    private BindingResult result;
    private Project testProject1,testProject2;
    @BeforeEach
    public void setUp(){
        initializeProjects();
        result = Mockito.mock(BindingResult.class);
    }
    @Test
    public void shouldNotCreateProject() {
        when(projectServicesMock.createProject(Mockito.any())).thenReturn(testProject1);
        List<Error> errorList = new LinkedList<>();
        initializeErrors(errorList);
        setUpMockErrors(errorList);
        ResponseEntity<Object> httpResponse = projectController.createProject(new ProjectRequest(testProject1),result);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST,httpResponse.getStatusCode());
        List<Error> errors = ((Map<String,List<Error>>) Objects.requireNonNull(httpResponse.getBody())).get("errors");
        Assertions.assertArrayEquals(errors.toArray(),errorList.toArray());
    }
    @Test
    public void shouldCreateProject(){
        when(projectServicesMock.createProject(Mockito.any())).thenReturn(testProject2);
        when(result.hasErrors()).thenReturn(false);
        ResponseEntity<Object> httpResponse = projectController.createProject(new ProjectRequest(testProject2),result);
        Assertions.assertEquals(HttpStatus.CREATED,httpResponse.getStatusCode());
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
        Finance testFinance1 = new Finance(1L,2,1L,2L,new Date(),new Date());
        testProject1 = new Project("testProject", "abc.com", "abc.com", "CO",
                "testDesc",testFinance1);
        testProject1.setId("id123");
        Finance testFinance2 = new Finance(1L,2,1L,200000,new Date(),new Date());
        testProject2 = new Project("testProject", "abc.com", "abc.com", "CO",
                "A large description to test",testFinance2);
        testProject2.setId("id1234");
    }


}
