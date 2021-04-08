package edu.escuelaing.ieti.startapp.web.handlers;

import edu.escuelaing.ieti.startapp.RabbitmqConfig;
import edu.escuelaing.ieti.startapp.business.model.Ticket;
import edu.escuelaing.ieti.startapp.business.services.ticketservices.ITicketServices;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

public class RabbitHandler {

    private ITicketServices ticketServices;
    @Autowired
    public RabbitHandler(ITicketServices ticketServices) {
        this.ticketServices = ticketServices;
    }

    @RabbitListener(queues = RabbitmqConfig.QUEUE_NAME)
    public void consumeMessageAndPublic(Ticket ticket){
        ticketServices.saveTicket(ticket);
        System.out.println("TICKET " + ticket);
    }
}
