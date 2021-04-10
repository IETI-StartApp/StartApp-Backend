package edu.escuelaing.ieti.startapp.business.services.ticketservices.impl;

import edu.escuelaing.ieti.startapp.business.model.Ticket;
import edu.escuelaing.ieti.startapp.business.model.User;
import edu.escuelaing.ieti.startapp.business.repositories.TicketRepository;
import edu.escuelaing.ieti.startapp.business.services.ticketservices.ITicketServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TicketServicesImpl implements ITicketServices {
    private TicketRepository ticketRepository;

    @Autowired
    public TicketServicesImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }


    @Override
    public List<Ticket> getTickets() {
        return ticketRepository.findAll();
    }

    @Override
    public Ticket saveTicket(Ticket ticket) {
        ticketRepository.save(ticket);
        return ticket;
    }

    @Override
    public List<Ticket> getTicketBySender(User sender) {

        return ticketRepository.findBySender(sender).orElse(new ArrayList<>());
    }

    @Override
    public List<Ticket> getTicketByReceptor(User receptor) {
        return ticketRepository.findByReceptor(receptor).orElse(new ArrayList<>());
    }
}
