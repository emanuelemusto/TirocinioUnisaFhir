package it.unisa.fhirconnection.fhirStarter.providers;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.annotation.*;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.param.StringParam;
import ca.uhn.fhir.rest.param.TokenParam;
import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.exceptions.InternalErrorException;
import it.unisa.fhirconnection.fhirStarter.database.MedicationToFhirMedication;
import it.unisa.fhirconnection.fhirStarter.service.*;
import it.unisa.fhirconnection.fhirStarter.model.PatientEntity;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

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
    public ArrayList<Medication> getAllbyPatient(@RequiredParam(name = Medication.SP_RES_ID) StringParam id, @RequiredParam(name = Patient.SP_IDENTIFIER) TokenParam theId, HttpServletRequest request) {
        String username = theId.getSystem();
        String token = theId.getValue();
        LogService.printLog(request.getRemoteAddr(), request.getRequestURL(), request.getMethod(), username);

        if (authorizeByPatientId(token, username, Integer.parseInt(String.valueOf(id.getValueNotNull())))) {

            PatientEntity patient = PatientService.getById(Integer.parseInt(String.valueOf(id.getValueNotNull())));
            ArrayList<Medication> medications = new ArrayList<>();
            for (it.unisa.fhirconnection.fhirStarter.model.Medication medication : patient.getMedications()) {
                medications.add(MedicationService.transform(medication));
            }
            return medications;

        } else {
            OperationOutcome oo = new OperationOutcome();
            throw new InternalErrorException("Token is expired", oo);
        }
    }

    @Search()
    public List<Medication> getAllApprovedMedication(@RequiredParam(name = Medication.SP_STATUS) StringParam param) {
        if ("true".equals(String.valueOf(param.getValueNotNull()))) {
            return new ArrayList<Medication>(MedicationService.medicationApproved());
        } else {
            return null;
        }
    }


}
