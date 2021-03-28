package edu.escuelaing.ieti.startapp.business;

import edu.escuelaing.ieti.startapp.business.model.Finance;
import edu.escuelaing.ieti.startapp.business.model.Project;
import edu.escuelaing.ieti.startapp.business.repositories.ProjectRepository;
import edu.escuelaing.ieti.startapp.business.services.projectservices.IProjectServices;
import edu.escuelaing.ieti.startapp.business.services.projectservices.impl.ProjectServicesImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class ProjectServiceTests {

    private ProjectRepository projectRepositoryMock = Mockito.mock(ProjectRepository.class);
    private IProjectServices projectServices = new ProjectServicesImpl(projectRepositoryMock);
    private Project testProject1;

    @BeforeEach
    void setUp(){
        setUpProjects();
    }

    @Test
    void shouldSaveProject(){
        when(projectRepositoryMock.save(Mockito.any())).thenReturn(testProject1);
        Project project = projectServices.createProject(testProject1);
        updateFinance(project);
        Assertions.assertEquals(project,testProject1);
    }

    private void updateFinance(Project project){
        Finance finance = project.getFinance();
        finance.setEndDate(new Date());
        finance.setStartDate(new Date());
        finance.setInvestorNumber(4);
        finance.setValue(300000);
        finance.setValuation(200000);
        finance.setMinimumInvestment(200000);
        project.setFinance(finance);
    }

    private void setUpProjects(){
        Finance testFinance1 = new Finance(200000,3,1L,2L,new Date(),new Date());
        testProject1 = new Project("testProject", "abc.com", "abc.com", "CO",
                "A valid description for a valid project",testFinance1);
    }
}
