package it.unisa.fhirconnection.fhirStarter.database;

import ca.uhn.fhir.context.FhirContext;
import it.unisa.fhirconnection.fhirStarter.model.PatientEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface PatientDAO extends CrudRepository<PatientEntity, Integer> {

    PatientEntity findByIdpatient(int idpatient);
}
