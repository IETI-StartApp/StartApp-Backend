package edu.escuelaing.ieti.startapp.web.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.escuelaing.ieti.startapp.business.exception.ProjectServiceException;
import edu.escuelaing.ieti.startapp.business.model.Project;
import edu.escuelaing.ieti.startapp.business.services.projectservices.IProjectServices;
import edu.escuelaing.ieti.startapp.web.handlers.ErrorHandler;
import edu.escuelaing.ieti.startapp.web.requests.ProjectRequest;


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
        ResponseEntity<Object> response;
        if (errorHandler.isValidRequest(result)){
            projectServices.createProject(project);
            response = new ResponseEntity<>(project,HttpStatus.CREATED);
        }else{
            response = errorHandler.getBadRequest(result);
        }
        return response;
    }
    @GetMapping()
    public ResponseEntity<Object> getAllProyects(){
    	return new ResponseEntity<>(projectServices.getAllProjects(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> getProyectById(@Valid @PathVariable String id){
    	ResponseEntity<Object> responseEntity;
    	try {
    		responseEntity =  new ResponseEntity<>(projectServices.getProyectById(id),HttpStatus.OK);
		} catch (ProjectServiceException e) {
			Map<String, String> error = new HashMap<>();
			error.put("Error", e.getMessage());
			responseEntity =  new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
		}
    	return responseEntity;
    }


}
