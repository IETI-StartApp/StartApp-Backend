package edu.escuelaing.ieti.startapp.web.requests;

import edu.escuelaing.ieti.startapp.business.model.Finance;
import edu.escuelaing.ieti.startapp.business.model.Project;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class ProjectRequest implements Serializable {

    private static final long serialVersionUID = 2L;
    @NotEmpty
    @Size(min=6,max=25,message = "El nombre del proyecto debe tener míninimo 6 carácteres y máximo 25")
    private String name;
    private String image;
    private String video;
    @NotEmpty(message = "El proyecto debe tener un país")
    private String country;
    @NotEmpty
    @Size(min=20,max=250,message = "Las descripción del proyecto debe tener míninimo 20 carácteres y máximo 250")
    private String description;
    @Valid
    private Finance finance;
    public ProjectRequest(){
    }
    public ProjectRequest(Project project){
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