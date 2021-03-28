package edu.escuelaing.ieti.startapp.web;

import edu.escuelaing.ieti.startapp.business.model.Finance;
import edu.escuelaing.ieti.startapp.business.model.Project;
import edu.escuelaing.ieti.startapp.business.services.projectservices.IProjectServices;
import edu.escuelaing.ieti.startapp.business.services.projectservices.impl.ProjectServicesImpl;
import edu.escuelaing.ieti.startapp.web.controllers.ProjectController;
import edu.escuelaing.ieti.startapp.web.requests.ProjectRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc

class ProjectControllerTests {

    private IProjectServices projectServicesMock = Mockito.mock(ProjectServicesImpl.class);
    private ProjectController projectController = new ProjectController(projectServicesMock);

    @Test
    void shouldCreateProject() {
        Finance financeTest = new Finance(1L,2,1L,2L,new Date(),new Date());
        Project testProject = new Project("test", "abc.com", "abc.com", "CO", "testDesc",financeTest);
        when(projectServicesMock.createProject(Mockito.any())).thenReturn(testProject);
        ResponseEntity<Object> httpResponse = projectController.createProject(new ProjectRequest(testProject));
        Assertions.assertEquals(HttpStatus.CREATED,httpResponse.getStatusCode());
    }

}
