package edu.escuelaing.ieti.startapp.web.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.escuelaing.ieti.startapp.business.exception.ProjectServiceException;
import edu.escuelaing.ieti.startapp.business.exception.UserServiceException;
import edu.escuelaing.ieti.startapp.business.model.Project;
import edu.escuelaing.ieti.startapp.business.model.User;
import edu.escuelaing.ieti.startapp.business.services.projectservices.IProjectServices;
import edu.escuelaing.ieti.startapp.business.services.projectservices.UserServices;
import edu.escuelaing.ieti.startapp.web.handlers.ErrorHandler;
import edu.escuelaing.ieti.startapp.web.requests.UserRequest;

@RestController
@RequestMapping(value = "api/v1/users")
public class UserController {
	private final ErrorHandler errorHandler;
	private final UserServices userServices;
	private final IProjectServices iProjectServices;
	private String key;

	@Autowired
	public UserController(UserServices userServices, IProjectServices iProjectServices) {
		errorHandler = new ErrorHandler();
		this.userServices = userServices;
		this.iProjectServices = iProjectServices;
		key= "Error";
	}

	@PostMapping()
	public ResponseEntity<Object> createUser(@Valid @RequestBody UserRequest userRequest, BindingResult result) {
		User user = userRequest.toUser();
		ResponseEntity<Object> responseEntity;
		if (errorHandler.isValidRequest(result)) {
			try {
				userServices.createUser(user);
				responseEntity = new ResponseEntity<>(user, HttpStatus.CREATED);
			} catch (UserServiceException e) {
				Map<String, String> error = new HashMap<>();
				error.put("Error", e.getMessage());
				responseEntity = new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
			}
			
		} else {
			responseEntity = errorHandler.getBadRequest(result);
		}
		return responseEntity;
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getUserById(@Valid @PathVariable String id) {
		ResponseEntity<Object> responseEntity;
		try {
			responseEntity = new ResponseEntity<>(userServices.getUserById(id), HttpStatus.OK);
		} catch (UserServiceException e) {
			Map<String, String> error = new HashMap<>();
			error.put(key, e.getMessage());
			responseEntity = new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
		}
		return responseEntity;
	}

	@GetMapping("/{identification}/{role}")
	public ResponseEntity<Object> getUserByIdentificationAndRole(@PathVariable long identification, @PathVariable String role) {
		ResponseEntity<Object> responseEntity;
		try {
			responseEntity = new ResponseEntity<>(userServices.getUserByIdentificationAndRole(identification, role), HttpStatus.OK);
		} catch (UserServiceException e) {
			Map<String, String> error = new HashMap<>();
			error.put(key, e.getMessage());
			responseEntity = new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
		}
		return responseEntity;
	}
	@GetMapping("/roles/{role}")
	public ResponseEntity<Object> getUserByRole(@PathVariable String role) {
		ResponseEntity<Object> responseEntity;
		try {
			responseEntity = new ResponseEntity<>(userServices.getUserByRole(role), HttpStatus.OK);
		}catch (UserServiceException e) {
			Map<String, String> error = new HashMap<>();
			error.put(key, e.getMessage());
			responseEntity = new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
		}
		return responseEntity;
	}
	@PutMapping("/{id}/project/{idProject}")
	public ResponseEntity<Object> addProjectToUser(@PathVariable String id, @PathVariable String idProject) {
		ResponseEntity<Object> responseEntity;
		try {
			Project project = iProjectServices.addInversion(iProjectServices.getProyectById(idProject));	
			responseEntity = new ResponseEntity<>(userServices.addProject(userServices.getUserById(id), project),HttpStatus.OK);
		} catch (ProjectServiceException | UserServiceException e) {
			Map<String, String> error = new HashMap<>();
			error.put(key, e.getMessage());
			responseEntity = new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
		}
		
		return responseEntity;
	}
}
