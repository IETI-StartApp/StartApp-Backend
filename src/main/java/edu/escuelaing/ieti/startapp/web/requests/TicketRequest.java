package edu.escuelaing.ieti.startapp.web.requests;

import edu.escuelaing.ieti.startapp.RabbitmqConfig;
import edu.escuelaing.ieti.startapp.business.model.enums.TicketStatus;
import edu.escuelaing.ieti.startapp.business.model.enums.TicketType;
import lombok.*;
import edu.escuelaing.ieti.startapp.business.model.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@ToString
@Component
public class TicketRequest {
    private Date creationDate;
    private User receptor;
    private User sender;
    private String description;
    private TicketStatus status;
    private TicketType type;

    public TicketRequest() {
    }

    public TicketRequest(Ticket ticket) {
        this.creationDate = ticket.getCreationDate();
        this.receptor = ticket.getReceptor();
        this.sender = ticket.getSender();
        this.description = ticket.getDescription();
        this.status = ticket.getStatus();
        this.type = ticket.getType();
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public User getReceptor() {
        return receptor;
    }

    public void setReceptor(User receptor) {
        this.receptor = receptor;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public TicketType getType() {
        return type;
    }

    public void setType(TicketType type) {
        this.type = type;
    }
    public Ticket toTicket(){
        return new Ticket(UUID.randomUUID().toString(),creationDate,sender,receptor,description,status,type);
    }

}
