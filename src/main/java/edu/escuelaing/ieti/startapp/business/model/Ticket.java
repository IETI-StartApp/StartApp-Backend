package edu.escuelaing.ieti.startapp.business.model;

import edu.escuelaing.ieti.startapp.business.model.enums.TicketStatus;
import edu.escuelaing.ieti.startapp.business.model.enums.TicketType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@NoArgsConstructor
@ToString
@Document(collection = "tickets")
public class Ticket {
    private static final long serialVersionUID = 1L;
    @Getter @Setter
    private String id;
    @Getter @Setter
    private Date creationDate;
    @Getter @Setter
    private User receptor;
    @Getter @Setter
    private User sender;
    @Getter @Setter
    private String description;
    @Getter @Setter
    private TicketStatus status;
    @Getter @Setter
    private TicketType type;

    public Ticket(String id,Date creationDate, User sender, User receptor, String description, TicketStatus status, TicketType type) {
        this.id = id;
        this.creationDate = creationDate;
        this.receptor = receptor;
        this.sender = sender;
        this.description = description;
        this.status = status;
        this.type = type;
    }

}
