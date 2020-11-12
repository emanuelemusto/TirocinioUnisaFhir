package it.unisa.fhirconnection.fhirStarter.database;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import it.unisa.fhirconnection.fhirStarter.model.User;


@Repository
public interface UserDAO extends CrudRepository<User, Integer> {
       User findByUsername(String username);
}
