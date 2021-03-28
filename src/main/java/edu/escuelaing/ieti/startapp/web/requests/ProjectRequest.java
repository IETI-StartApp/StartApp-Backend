package edu.escuelaing.ieti.startapp.web.requests;

import edu.escuelaing.ieti.startapp.business.model.Finance;
import edu.escuelaing.ieti.startapp.business.model.Project;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Finance getFinance() {
        return finance;
    }

    public void setFinance(Finance finance) {
        this.finance = finance;
    }
}
