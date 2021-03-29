package edu.escuelaing.ieti.startapp.business.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import edu.escuelaing.ieti.startapp.business.model.Project;

public interface ProjectRepository extends MongoRepository<Project, String> {

}
