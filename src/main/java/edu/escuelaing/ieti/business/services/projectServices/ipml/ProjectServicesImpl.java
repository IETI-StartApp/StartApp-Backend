package edu.escuelaing.ieti.business.services.projectServices.ipml;

import edu.escuelaing.ieti.business.model.Project;
import edu.escuelaing.ieti.business.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.escuelaing.ieti.business.services.projectServices.ProjectServices;

@Service
public class ProjectServicesImpl implements ProjectServices {

    @Autowired
    ProjectRepository projectRepository;
    @Override
    public Project createProject(Project project) {
        projectRepository.save(project);
        return project;
    }
}
