package edu.escuelaing.ieti.startapp.business.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import edu.escuelaing.ieti.startapp.business.model.Chat;
import edu.escuelaing.ieti.startapp.business.model.User; 

public interface ChatRepository extends MongoRepository<Chat, String> {
	Optional<List<Chat>> findByInvestor(User investor);
	Optional<List<Chat>> findByEntrepreneur(User entrepreneur);
}