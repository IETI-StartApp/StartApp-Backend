package edu.escuelaing.ieti.business.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@Document(collection = "projects")
public class Project implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	@Getter @Setter
	private String name;
	@Getter @Setter
	private String image;
	@Getter @Setter
	private User user;
	@Getter @Setter
	private String video;
	@Getter @Setter
	private String country;
	@Getter @Setter
	private String description;
	@Getter @Setter
	private Finance finance;

	public Project() {
	}

	public Project(String name, String image, User user, String video, String country, String description,Finance finance) {
		this.name = name;
		this.image = image;
		this.user = user;
		this.video = video;
		this.country = country;
		this.description = description;
		this.finance = finance;
	}


}
