package edu.escuelaing.ieti.startapp.web.controllers;

import edu.escuelaing.ieti.startapp.business.model.Ticket;
import edu.escuelaing.ieti.startapp.business.model.User;
import edu.escuelaing.ieti.startapp.business.services.ticketservices.ITicketServices;
import edu.escuelaing.ieti.startapp.web.requests.TicketRequest;
import edu.escuelaing.ieti.startapp.web.requests.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/support/tickets")
@CrossOrigin(origins= "*")
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
    @GetMapping()
    public ResponseEntity<Object> getTickets(){
        return new ResponseEntity<>(ticketServices.getTickets(),HttpStatus.OK);
    }

    @GetMapping("/Receptor")
    public ResponseEntity<Object> getTicketByReceptor(@RequestBody UserRequest userRequest){
        User user = userRequest.toUser();
        return new ResponseEntity<>(ticketServices.getTicketByReceptor(user),HttpStatus.OK);
    }

    @GetMapping("/Sender")
    public ResponseEntity<Object> getTicketBySender(@RequestBody UserRequest userRequest){
        User user = userRequest.toUser();
        return new ResponseEntity<>(ticketServices.getTicketBySender(user),HttpStatus.OK);
    }






}
