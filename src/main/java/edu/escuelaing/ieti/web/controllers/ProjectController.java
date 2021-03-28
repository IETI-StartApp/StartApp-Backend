package edu.escuelaing.ieti.web.controllers;

import edu.escuelaing.ieti.business.model.Project;
import edu.escuelaing.ieti.business.services.projectServices.ProjectServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/projects")
public class ProjectController {

	private final ProjectServices projectServices;

	@Autowired
	public ProjectController(ProjectServices projectServices) {
		this.projectServices = projectServices;
	}

	@PostMapping()
	public ResponseEntity<Project> createProject(@RequestBody Project project){
		projectServices.createProject(project);

		return new ResponseEntity<>(project,HttpStatus.CREATED);
	}

}
