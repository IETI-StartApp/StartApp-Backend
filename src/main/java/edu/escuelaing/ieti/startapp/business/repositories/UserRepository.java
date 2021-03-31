package edu.escuelaing.ieti.startapp.business.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import edu.escuelaing.ieti.startapp.business.model.User;

public interface UserRepository extends MongoRepository<User, String>{
	public Optional<User> findByIdentificationAndRole(long identification, String role);
	public Optional<List<User>> findByRole(String role);
}
