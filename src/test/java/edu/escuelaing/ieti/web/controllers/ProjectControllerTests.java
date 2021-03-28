package edu.escuelaing.ieti.web.controllers;

import edu.escuelaing.ieti.business.model.Finance;
import edu.escuelaing.ieti.business.model.Project;
import edu.escuelaing.ieti.business.model.User;
import edu.escuelaing.ieti.business.services.projectServices.ProjectServices;
import edu.escuelaing.ieti.business.services.projectServices.ipml.ProjectServicesImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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

    private ProjectServices projectServicesMock = Mockito.mock(ProjectServicesImpl.class);
    private ProjectController projectController = new ProjectController(projectServicesMock);

    @Test
    void shouldCreateProject() {
        Finance financeTest = new Finance(1L,2,1L,2L,new Date(),new Date());
        Project testProject = new Project("test", "abc.com", new User(), "abc.com", "CO", "testDesc",financeTest);
        when(projectServicesMock.createProject(Mockito.any())).thenReturn(testProject);
        ResponseEntity<Project> httpResponse = projectController.createProject(testProject);
        Assert.assertEquals(httpResponse.getStatusCode(), HttpStatus.CREATED);
    }

}
