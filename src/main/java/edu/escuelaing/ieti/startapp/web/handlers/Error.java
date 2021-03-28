package edu.escuelaing.ieti.startapp.web.handlers;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Error {
    @Getter @Setter
    private String field;
    @Getter @Setter
    private String message;
    public Error(String field, String message){
        this.field = field;
        this.message = message;
    }

}
