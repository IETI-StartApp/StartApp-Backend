package edu.escuelaing.ieti.startapp.business.model;



import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import lombok.Getter;
import lombok.Setter;

@Document(collection = "comments")
public class Comment implements Serializable {
	private static final long serialVersionUID = 1L;
    @Id
    @Getter @Setter
    private String id;
    @Getter @Setter
    private User idUser;
    @Getter @Setter
    private Project idProject;
    @Getter @Setter
    private String data;
    @Getter @Setter 
    private int rate;
    
    public Comment(User idUser, Project idProject, String data, int rate) {
    	this.idUser = idUser;
    	this.idProject = idProject;
    	this.data = data;
    	this.rate = rate;
    }
	
	
}
