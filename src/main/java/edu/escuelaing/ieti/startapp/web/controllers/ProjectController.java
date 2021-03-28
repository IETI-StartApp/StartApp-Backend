package edu.escuelaing.ieti.startapp.web.controllers;

import edu.escuelaing.ieti.startapp.business.model.Project;
import edu.escuelaing.ieti.startapp.business.services.projectservices.IProjectServices;
import edu.escuelaing.ieti.startapp.web.requests.ProjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/projects")
public class ProjectController {

    private final IProjectServices projectServices;

    @Autowired
    public ProjectController(IProjectServices projectServices) {
        this.projectServices = projectServices;
    }

    @PostMapping()
    public ResponseEntity<Object> createProject(@RequestBody ProjectRequest projectRequest){
        Project project = projectRequest.toProject();
        projectServices.createProject(project);
        return new ResponseEntity<>(project,HttpStatus.CREATED);
    }

}
