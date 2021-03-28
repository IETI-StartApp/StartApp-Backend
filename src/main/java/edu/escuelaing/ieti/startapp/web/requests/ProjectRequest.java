package edu.escuelaing.ieti.startapp.web.requests;

import edu.escuelaing.ieti.startapp.business.model.Finance;
import edu.escuelaing.ieti.startapp.business.model.Project;

import java.io.Serializable;

public class ProjectRequest implements Serializable {

    private static final long serialVersionUID = 2L;
    private String id;
    private String name;
    private String image;
    private String video;
    private String country;
    private String description;
    private Finance finance;

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
