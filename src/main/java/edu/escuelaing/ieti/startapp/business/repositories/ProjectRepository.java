package edu.escuelaing.ieti.startapp.business.repositories;

import edu.escuelaing.ieti.startapp.business.model.Project;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProjectRepository extends MongoRepository<Project, String> {

}
