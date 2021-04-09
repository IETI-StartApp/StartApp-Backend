package edu.escuelaing.ieti.startapp.web;

import edu.escuelaing.ieti.startapp.business.model.Ticket;
import edu.escuelaing.ieti.startapp.business.model.User;
import edu.escuelaing.ieti.startapp.business.model.enums.TicketStatus;
import edu.escuelaing.ieti.startapp.business.model.enums.TicketType;
import edu.escuelaing.ieti.startapp.business.services.ticketservices.ITicketServices;
import edu.escuelaing.ieti.startapp.business.services.ticketservices.impl.TicketServicesImpl;
import edu.escuelaing.ieti.startapp.web.controllers.TicketController;
import edu.escuelaing.ieti.startapp.web.requests.TicketRequest;
import edu.escuelaing.ieti.startapp.web.requests.UserRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class TicketControllerTests {
    private ITicketServices ticketServicesMock = Mockito.mock(TicketServicesImpl.class);
    private TicketController ticketController = new TicketController(ticketServicesMock);
    private TicketRequest testTicket;
    private List<Ticket> ticketList;

    @BeforeEach
    void setUp(){
        ticketList = new ArrayList<>();
        Ticket ticket = new Ticket("123",new Date(),new User(),new User(),"abc",TicketStatus.OPEN,TicketType.FINANCIAL);
        Ticket ticket2 = new Ticket("1234",new Date(),new User(),new User(),"abc",TicketStatus.OPEN,TicketType.ADMINISTRATIVE);
        ticketList.add(ticket);
        ticketList.add(ticket2);
        testTicket = new TicketRequest(ticket);
    }
    @Test
    void shouldPublishTicket(){
        when(ticketServicesMock.saveTicket(Mockito.any())).thenReturn(ticketList.get(0));
        ResponseEntity<Object> response = ticketController.publishTicket(testTicket);
        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
    }
    @Test
    void shouldGetTickets(){
        when(ticketServicesMock.getTickets()).thenReturn(ticketList);
        ResponseEntity<Object> response = ticketController.getTickets();
        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
    }
    @Test
    void shouldGetTicketsByReceptor(){
        when(ticketServicesMock.getTicketByReceptor(Mockito.any())).thenReturn(ticketList);
        ResponseEntity<Object> response = ticketController.getTicketByReceptor(new UserRequest());
        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    void shouldGetTicketBySender(){
        when(ticketServicesMock.getTicketBySender(Mockito.any())).thenReturn(ticketList);
        ResponseEntity<Object> response = ticketController.getTicketBySender(new UserRequest());
        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
    }
}
