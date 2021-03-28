package edu.escuelaing.ieti.startapp.web.requests;

import edu.escuelaing.ieti.startapp.business.model.Finance;
import edu.escuelaing.ieti.startapp.business.model.Project;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

public class ProjectRequest implements Serializable {

    private static final long serialVersionUID = 1L;
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

    public ProjectRequest(){
    }
    public ProjectRequest(String id, String name, String image, String video, String country, String description, Finance finance) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.video = video;
        this.country = country;
        this.description = description;
        this.finance = finance;
    }
    public ProjectRequest(Project project){
        this.id = project.getId();
        this.name = project.getName();
        this.image = project.getImage();
        this.country = project.getCountry();
        this.finance = project.getFinance();
        this.description = project.getDescription();
        this.video = project.getVideo();
    }
    public Project toProject (){
        return new Project(name,image,video,country,description,finance);
    }
}
