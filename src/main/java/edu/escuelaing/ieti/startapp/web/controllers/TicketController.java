package edu.escuelaing.ieti.startapp.web.controllers;

import edu.escuelaing.ieti.startapp.RabbitmqConfig;
import edu.escuelaing.ieti.startapp.business.model.Ticket;
import edu.escuelaing.ieti.startapp.business.services.ticketservices.ITicketServices;
import edu.escuelaing.ieti.startapp.web.requests.TicketRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/support/tickets")
public class TicketController {

    private ITicketServices ticketServices;
    @Autowired
    public TicketController(ITicketServices ticketServices) {
        this.ticketServices = ticketServices;
    }

    @PostMapping()
    public ResponseEntity<Object> publishTicket(@RequestBody TicketRequest ticketRequest){
        Ticket ticket = ticketRequest.toTicket();
        ticketServices.saveTicket(ticket);
        return new ResponseEntity<>(ticket.getId(), HttpStatus.OK);
    }





}
