package edu.escuelaing.ieti.startapp.business.services.ticketservices;

import edu.escuelaing.ieti.startapp.business.model.Ticket;
import edu.escuelaing.ieti.startapp.business.model.User;
import edu.escuelaing.ieti.startapp.business.model.enums.TicketStatus;
import edu.escuelaing.ieti.startapp.business.model.enums.TicketType;

import java.util.List;

public interface ITicketServices {
    public List<Ticket> getTickets();
    public Ticket saveTicket(Ticket ticket);
    public List<Ticket> getTicketBySender(User sender);
    public List<Ticket> getTicketByReceptor(User receptor);
    

}
