package edu.escuelaing.ieti.startapp.business.services.ticketservices;

import edu.escuelaing.ieti.startapp.business.model.Ticket;
import edu.escuelaing.ieti.startapp.business.model.User;

import java.util.List;

public interface ITicketServices {
    List<Ticket> getTickets();
    Ticket saveTicket(Ticket ticket);
    List<Ticket> getTicketBySender(User sender);
    List<Ticket> getTicketByReceptor(User receptor);
    

}
