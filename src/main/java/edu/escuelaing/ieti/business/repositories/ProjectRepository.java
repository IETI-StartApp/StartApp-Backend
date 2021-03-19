package edu.escuelaing.ieti.business.repositories;


import edu.escuelaing.ieti.business.model.Project;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProjectRepository extends MongoRepository<Project, String> {

}
