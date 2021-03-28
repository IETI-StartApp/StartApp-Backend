package edu.escuelaing.ieti.startapp.business.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Document(collection = "projects")
public class Project implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Getter @Setter
    private String id;
    @Getter @Setter
    private String name;
    @Getter @Setter
    private String image;
    @Getter @Setter
    private String video;
    @Getter @Setter
    private String country;
    @Getter @Setter
    private String description;
    @Getter @Setter
    private Finance finance;

    public Project(String name, String image, String video, String country, String description,Finance finance) {
        this.name = name;
        this.image = image;
        this.video = video;
        this.country = country;
        this.description = description;
        this.finance = finance;
    }


}
