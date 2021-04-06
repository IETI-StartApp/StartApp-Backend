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

import edu.escuelaing.ieti.startapp.business.exception.ProjectServiceException;
import edu.escuelaing.ieti.startapp.business.model.Finance;
import edu.escuelaing.ieti.startapp.business.model.Project;
import edu.escuelaing.ieti.startapp.business.repositories.ProjectRepository;
import edu.escuelaing.ieti.startapp.business.services.projectservices.IProjectServices;
import edu.escuelaing.ieti.startapp.business.services.projectservices.impl.ProjectServicesImpl;

@SpringBootTest
@AutoConfigureMockMvc
class ProjectServiceTests {

    private ProjectRepository projectRepositoryMock = Mockito.mock(ProjectRepository.class);
    private IProjectServices projectServices = new ProjectServicesImpl(projectRepositoryMock);
    private Project testProject1;
	private List<Project> projects;

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
    @Test
    void souldGetAllProjects() {
    	when(projectRepositoryMock.findAll()).thenReturn(projects);
    	Assertions.assertEquals(projects,  projectServices.getAllProjects());
    }
    @Test
    //thenReturn(thenTrow
    void souldGetProjectById() {
    	when(projectRepositoryMock.findById(Mockito.anyString())).thenReturn(Optional.of(testProject1));
    	try {
			Project project = projectServices.getProyectById(testProject1.getId());
			Assertions.assertEquals(testProject1, project);
		} catch (ProjectServiceException e) {
			System.out.println(e.getMessage());
			Assertions.fail();
		}
    	
    }
    @Test
    void souldNotGetProjectById() {
    	when(projectRepositoryMock.findById(Mockito.anyString())).thenReturn(Optional.empty());
    	try {
			Project project = projectServices.getProyectById("test");
			Assertions.fail();
		} catch (ProjectServiceException e) {
			Assertions.assertEquals(ProjectServiceException.PROJECT_NOT_FOUND_EXCEPTION, e.getMessage());
		}
    }
    @Test
    void souldAddInversion() {
    	when(projectRepositoryMock.save(Mockito.any())).thenReturn(testProject1);
    	 Project project = projectServices.addInversion(testProject1);
    	 Assertions.assertEquals(project, testProject1);
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
    	projects = new ArrayList<Project>();
        Finance testFinance1 = new Finance(200000,3,1L,2L,new Date(),new Date());
        testProject1 = new Project("testProject", "abc.com", "abc.com", "CO",
                "A valid description for a valid project",testFinance1);
        testProject1.setId("Test");
        projects.add(testProject1);
    }
}
