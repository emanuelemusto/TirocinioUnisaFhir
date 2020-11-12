package it.unisa.fhirconnection.fhirStarter.database;

import it.unisa.fhirconnection.fhirStarter.model.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonDAO extends CrudRepository<Person, Integer> {

}
