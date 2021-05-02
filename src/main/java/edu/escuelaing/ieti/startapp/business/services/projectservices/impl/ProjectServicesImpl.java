package edu.escuelaing.ieti.startapp.business.services.projectservices.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.escuelaing.ieti.startapp.business.exception.ProjectServiceException;
import edu.escuelaing.ieti.startapp.business.model.Finance;
import edu.escuelaing.ieti.startapp.business.model.Project;
import edu.escuelaing.ieti.startapp.business.repositories.ProjectRepository;
import edu.escuelaing.ieti.startapp.business.services.projectservices.IProjectServices;

@Service
public class ProjectServicesImpl implements IProjectServices {

    private final ProjectRepository projectRepository;
    @Autowired
    public ProjectServicesImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Project createProject(Project project) {
        projectRepository.save(project);
        return project;
    }
	@Override
    public List<Project> getAllProjects(){
    	return projectRepository.findAll();
    }
	@Override
	public Project getProyectById(String id) throws ProjectServiceException {
		return projectRepository.findById(id)
				.orElseThrow(() ->
				new ProjectServiceException(ProjectServiceException.PROJECT_NOT_FOUND_EXCEPTION));
	}
	
	@Override
	public Project addInversion(Project project) {
		Finance finance = project.getFinance();
		finance.setInvestorNumber(finance.getInvestorNumber()+1);
		project.setFinance(finance);
		projectRepository.save(project);
		return project;
	}
}
