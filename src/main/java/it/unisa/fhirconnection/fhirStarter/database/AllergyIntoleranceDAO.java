package it.unisa.fhirconnection.fhirStarter.database;

import it.unisa.fhirconnection.fhirStarter.model.AllergyIntolerance;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllergyIntoleranceDAO extends CrudRepository<AllergyIntolerance, Integer> {
}
