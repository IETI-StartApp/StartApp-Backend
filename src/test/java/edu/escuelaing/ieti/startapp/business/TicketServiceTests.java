package edu.escuelaing.ieti.startapp.business;

import edu.escuelaing.ieti.startapp.business.model.Ticket;
import edu.escuelaing.ieti.startapp.business.model.User;
import edu.escuelaing.ieti.startapp.business.model.enums.TicketStatus;
import edu.escuelaing.ieti.startapp.business.model.enums.TicketType;
import edu.escuelaing.ieti.startapp.business.repositories.ProjectRepository;
import edu.escuelaing.ieti.startapp.business.repositories.TicketRepository;
import edu.escuelaing.ieti.startapp.business.services.projectservices.IProjectServices;
import edu.escuelaing.ieti.startapp.business.services.projectservices.impl.ProjectServicesImpl;
import edu.escuelaing.ieti.startapp.business.services.ticketservices.ITicketServices;
import edu.escuelaing.ieti.startapp.business.services.ticketservices.impl.TicketServicesImpl;
import edu.escuelaing.ieti.startapp.web.requests.TicketRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

class TicketServiceTests {
    private TicketRepository ticketRepositoryMock = Mockito.mock(TicketRepository.class);
    private ITicketServices ticketServices = new TicketServicesImpl(ticketRepositoryMock);
    private List<Ticket> ticketList;
    @BeforeEach
    void setUp(){
        ticketList = new ArrayList<>();
        Ticket ticket = new Ticket("123",new Date(),new User(),new User(),"abc", TicketStatus.OPEN, TicketType.FINANCIAL);
        Ticket ticket2 = new Ticket("1234",new Date(),new User(),new User(),"abc",TicketStatus.OPEN,TicketType.ADMINISTRATIVE);
        ticketList.add(ticket);
        ticketList.add(ticket2);
    }
    @Test
    void shouldSaveTicket(){
        when(ticketRepositoryMock.save(Mockito.any())).thenReturn(ticketList.get(0));
        Ticket ticketReturn = ticketServices.saveTicket(ticketList.get(0));
        Assertions.assertEquals(ticketList.get(0),ticketReturn);
    }
    @Test
    void shouldGetTickets(){
        when(ticketRepositoryMock.findAll()).thenReturn(ticketList);
        List<Ticket> ticketsReturn = ticketServices.getTickets();
        Assertions.assertEquals(ticketList,ticketsReturn);
    }
    @Test
    void shouldGetTicketsBySender(){
        when(ticketRepositoryMock.findBySender(Mockito.any())).thenReturn(Optional.of(ticketList));
        List<Ticket> ticketsReturn = ticketServices.getTicketBySender(new User());
        Assertions.assertEquals(ticketList,ticketsReturn);
    }
    @Test
    void shouldGetTicketsByReceptor(){
        when(ticketRepositoryMock.findByReceptor(Mockito.any())).thenReturn(Optional.of(ticketList));
        List<Ticket> ticketsReturn = ticketServices.getTicketByReceptor(new User());
        Assertions.assertEquals(ticketList,ticketsReturn);
    }
}
