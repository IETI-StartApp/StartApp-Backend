package edu.escuelaing.ieti.startapp.business.services.projectservices.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import edu.escuelaing.ieti.startapp.business.exception.UserServiceException;
import edu.escuelaing.ieti.startapp.business.model.Project;
import edu.escuelaing.ieti.startapp.business.model.User;
import edu.escuelaing.ieti.startapp.business.repositories.UserRepository;
import edu.escuelaing.ieti.startapp.business.services.projectservices.UserServices;

@Service
public class UserServicesImpl implements UserServices {
	private final UserRepository userRepository;

	@Autowired
	public UserServicesImpl(UserRepository userRepository) {
		this.userRepository = userRepository;

	}

	@Override
	public User createUser(User user) throws UserServiceException {
		try {
			userRepository.save(user);
		} catch (DuplicateKeyException e) {
			throw new UserServiceException(UserServiceException.USER_REPEATED_EXCEPTION);
		}

		return user;
	}

	@Override
	public User getUserById(String id) throws UserServiceException {
		return userRepository.findById(id)
				.orElseThrow(() -> new UserServiceException(UserServiceException.USER_NOT_FOUND_EXCEPTION));
	}

	@Override
	public User getUserByIdentificationAndRole(long identification, String role) throws UserServiceException {
		return userRepository.findByIdentificationAndRole(identification, role)
				.orElseThrow(() -> new UserServiceException(UserServiceException.USER_NOT_FOUND_EXCEPTION));
	}

	@Override
	public List<User> getUserByRole(String role) throws UserServiceException {
		return userRepository.findByRole(role)
				.orElseThrow(() -> new UserServiceException(UserServiceException.USER_ROLE_NOT_FOUND_EXCEPTION));
	}

	@Override
	public User addProject(User user, Project project) {
		List<Project> projects = user.getProjects();
		projects.add(project);
		user.setProjects(projects);
		userRepository.save(user);
		return user;
	}
	@Override
	public User getUserByEmail(String email) throws UserServiceException {
		return userRepository.findByEmail(email)
				.orElseThrow(() -> new UserServiceException(UserServiceException.USER_NOT_FOUND_EXCEPTION));
	}
}
