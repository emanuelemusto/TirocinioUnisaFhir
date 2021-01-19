package it.unisa.fhirconnection.fhirStarter.providers;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.annotation.*;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.param.StringParam;
import ca.uhn.fhir.rest.param.TokenParam;
import ca.uhn.fhir.rest.server.IResourceProvider;
import it.unisa.fhirconnection.fhirStarter.database.MedicationToFhirMedication;
import it.unisa.fhirconnection.fhirStarter.service.DiagnosticReportService;
import it.unisa.fhirconnection.fhirStarter.service.MedicationService;
import it.unisa.fhirconnection.fhirStarter.service.PatientService;
import it.unisa.fhirconnection.fhirStarter.model.PatientEntity;
import it.unisa.fhirconnection.fhirStarter.service.ProblemService;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

import static it.unisa.fhirconnection.fhirStarter.service.UserService.authorizeByPatientId;

@Component
public class MedicationProvider implements IResourceProvider {

    @Autowired
    FhirContext ctx;


    private static final Logger log = LoggerFactory.getLogger(MedicationProvider.class);

    @Override
    public Class<? extends IBaseResource> getResourceType() {
        return Medication.class;
    }

    /*@Create
    public MethodOutcome create(@ResourceParam MedicationProvider medicationProvider) {

        log.debug("Create MedicationProvider Provider called");

        MethodOutcome method = new MethodOutcome();
        method.setCreated(true);
        OperationOutcome opOutcome = new OperationOutcome();
        method.setOperationOutcome(opOutcome);


        log.debug("called create MedicationProvider method");

        return method;
    }*/


    @Read()
    public ArrayList<Medication> getAllbyPatient(@IdParam IdType internalId) {
        PatientEntity patient = PatientService.getById(Integer.parseInt(internalId.getIdPart()));
        ArrayList<Medication> medications = new ArrayList<>();

        for (it.unisa.fhirconnection.fhirStarter.model.Medication medication : patient.getMedications()) {
            medications.add(MedicationService.transform(medication));


        }

        return medications;
    }

    @Search()
    public ArrayList<Medication> getAllbyPatient(@RequiredParam(name = Medication.SP_RES_ID) StringParam id, @RequiredParam(name=Patient.SP_IDENTIFIER) TokenParam theId ) {
        String username = theId.getSystem();
        String token = theId.getValue();


        if(authorizeByPatientId(token,username,Integer.parseInt(String.valueOf(id.getValueNotNull())))) {
            System.out.println("medication ");

            PatientEntity patient = PatientService.getById(Integer.parseInt(String.valueOf(id.getValueNotNull())));
            ArrayList<Medication> medications = new ArrayList<>();

            for (it.unisa.fhirconnection.fhirStarter.model.Medication medication : patient.getMedications()) {
                medications.add(MedicationService.transform(medication));


            }

            return medications;
        }
        return null;
    }




}
