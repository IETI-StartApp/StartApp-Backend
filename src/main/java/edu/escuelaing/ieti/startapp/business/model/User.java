package edu.escuelaing.ieti.startapp.business.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import edu.escuelaing.ieti.startapp.business.model.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

@Document(collection = "users")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Getter
	@Setter
	private String id;
	@Getter
	@Setter
	private String firstName;
	@Getter
	@Setter
	private String lastName; 
	@Getter
	@Setter
	private String email;
	@Getter
	@Setter
	private long identification;
	@Getter
	@Setter
	private UserRole role;
	@Getter
	@Setter
	private String description;
	@Getter
	@Setter
	private List<Project> projects;
	@Getter
	@Setter
	private List<Ticket> tickets;
	public User(String firstName, String lastName, String email, long identification, UserRole role, String description,
			List<Project> projects, List<Ticket> tickets) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.identification = identification;
		this.role = role;
		this.description = description;
		this.projects = projects;
		this.tickets = tickets;
	}


}
