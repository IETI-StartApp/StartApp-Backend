package edu.escuelaing.ieti.startapp.business.services.projectservices;


import java.util.List;

import edu.escuelaing.ieti.startapp.business.exception.ProjectServiceException;
import edu.escuelaing.ieti.startapp.business.model.Project;

public interface IProjectServices {
    Project createProject(Project project);

	List<Project> getAllProjects();

	Project getProyectById(String id) throws ProjectServiceException;
}
