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

	@Autowired
	ProjectServices projectServices;
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> obtenerUsuario(@PathVariable int id) {
		return new ResponseEntity<>("Prueba", HttpStatus.ACCEPTED);
	}
	@PostMapping()
	public ResponseEntity<?> createProject(@RequestBody Project project){
		projectServices.createProject(project);
		return new ResponseEntity<>(project,HttpStatus.CREATED);
	}

}
