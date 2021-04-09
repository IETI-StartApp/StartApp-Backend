package edu.escuelaing.ieti.startapp.business.repositories;

import edu.escuelaing.ieti.startapp.business.model.Ticket;
import edu.escuelaing.ieti.startapp.business.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends MongoRepository<Ticket, String> {
    public Optional<List<Ticket>> findBySender(User sender);
    public Optional<List<Ticket>> findByReceptor(User receptor);

}
