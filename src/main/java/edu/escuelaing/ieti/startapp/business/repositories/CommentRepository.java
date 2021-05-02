package edu.escuelaing.ieti.startapp.business.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import edu.escuelaing.ieti.startapp.business.model.Comment;
import edu.escuelaing.ieti.startapp.business.model.Project;


public interface CommentRepository extends MongoRepository<Comment, String> {
	public Optional<List<Comment>>findByIdProject(Project idProject);
}
