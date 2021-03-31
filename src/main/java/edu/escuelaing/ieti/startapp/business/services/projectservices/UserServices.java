package edu.escuelaing.ieti.startapp.business.services.projectservices;

import java.util.List;

import edu.escuelaing.ieti.startapp.business.exception.UserServiceException;
import edu.escuelaing.ieti.startapp.business.model.User;

public interface UserServices {

	User createUser(User user) throws UserServiceException;

	User getUserById(String id) throws UserServiceException;

	User getUserByIdentificationAndRole(long identification, String role) throws UserServiceException;

	List<User> getUserByRole(String role) throws UserServiceException;

}
