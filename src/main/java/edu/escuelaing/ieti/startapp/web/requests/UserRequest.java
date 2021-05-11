package edu.escuelaing.ieti.startapp.web.requests;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import edu.escuelaing.ieti.startapp.business.model.Project;
import edu.escuelaing.ieti.startapp.business.model.User;
import edu.escuelaing.ieti.startapp.business.model.enums.UserRole;

public class UserRequest {
	@NotEmpty
	@Size(min = 3, max = 30, message = "El nombre del usuario debe tener maximo 4 caracteres y minimo 30")
	private String firstName;
	@NotEmpty
	@Size(min = 3, max = 30, message = "El nombre del usuario debe tener maximo 4 caracteres y minimo 30")
	private String lastName;
	@Email
	private String email;
	@Min(value = 1, message = "La cedula es incorrecta")
	private long identification;
	private UserRole role;
	private String description;
	private List<Project> projects;
	private String image;

	public UserRequest() {

	}

	public UserRequest(User user) {
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.email = user.getEmail();
		this.identification = user.getIdentification();
		this.role = user.getRole();
		this.description = user.getDescription();
		this.projects = user.getProjects();
		this.image = user.getImage();
	}

	public User toUser() {
		return new User(firstName, lastName, email, identification, role, description, projects, image);
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getIdentification() {
		return identification;
	}

	public void setIdentification(long identification) {
		this.identification = identification;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	

}
