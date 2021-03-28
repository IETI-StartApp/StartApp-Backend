package edu.escuelaing.ieti.startapp.business.services.projectservices.impl;

import edu.escuelaing.ieti.startapp.business.model.Project;
import edu.escuelaing.ieti.startapp.business.repositories.ProjectRepository;
import edu.escuelaing.ieti.startapp.business.services.projectservices.IProjectServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
