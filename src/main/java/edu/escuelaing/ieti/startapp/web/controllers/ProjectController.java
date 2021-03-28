package edu.escuelaing.ieti.startapp.web.controllers;

import edu.escuelaing.ieti.startapp.business.model.Project;
import edu.escuelaing.ieti.startapp.business.services.projectservices.IProjectServices;
import edu.escuelaing.ieti.startapp.web.handlers.ErrorHandler;
import edu.escuelaing.ieti.startapp.web.requests.ProjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "api/v1/projects")
public class ProjectController {

    private final IProjectServices projectServices;
    private final ErrorHandler errorHandler;
    @Autowired
    public ProjectController(IProjectServices projectServices) {
        this.projectServices = projectServices;
        errorHandler = new ErrorHandler();
    }

    @PostMapping()
    public ResponseEntity<Object> createProject(@Valid @RequestBody ProjectRequest projectRequest, BindingResult result){
        Project project = projectRequest.toProject();
        projectServices.createProject(project);
        ResponseEntity<Object> response = errorHandler.isValidRequest(result)
                ? new ResponseEntity<>(project,HttpStatus.CREATED)
                : errorHandler.getBadRequest(result);
        return response;
    }



}
