package it.unisa.fhirconnection.fhirStarter.providers;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.annotation.*;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.param.StringParam;
import ca.uhn.fhir.rest.param.TokenParam;
import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.exceptions.InternalErrorException;
import it.unisa.fhirconnection.fhirStarter.service.LogService;
import it.unisa.fhirconnection.fhirStarter.service.PatientService;
import it.unisa.fhirconnection.fhirStarter.database.FHIRPatienttoPatientEntity;
import it.unisa.fhirconnection.fhirStarter.model.PatientEntity;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import it.unisa.fhirconnection.fhirStarter.database.PatientDAO;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

import static it.unisa.fhirconnection.fhirStarter.service.UserService.authorize;

@Component
public class PatientProvider implements IResourceProvider {

    @Autowired
    FhirContext ctx;

    private static FHIRPatienttoPatientEntity fhirPatienttoPatientEntity;

    @Autowired
    private static PatientDAO patientDAO;

    @Autowired
    private static PatientService patientService;

    private static final Logger log = LoggerFactory.getLogger(PatientProvider.class);

    @Override
    public Class<? extends IBaseResource> getResourceType() {
        return Patient.class;
    }

    @Create
    public MethodOutcome createPatient(@ResourceParam Patient patient) {

        log.debug("Create Patient Provider called");

        MethodOutcome method = new MethodOutcome();
        method.setCreated(true);
        OperationOutcome opOutcome = new OperationOutcome();
        method.setOperationOutcome(opOutcome);

        try {
            PatientEntity patientEntity = fhirPatienttoPatientEntity.transform(patient);
            patientDAO.save(patientEntity);
            log.info(String.valueOf(patientEntity.getIdpatient()));
            method.setId(patient.getIdElement());
            method.setResource(patient);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }

        log.debug("called create Patient method");

        return method;
    }

    @Read()
    public Patient readPatient(@IdParam IdType internalId, HttpServletRequest request) {
        PatientEntity patient = PatientService.getById(Integer.parseInt(internalId.getIdPart()));
        LogService.printLog(request.getRemoteAddr(), request.getRequestURL(), request.getMethod(), patient.getPerson().getUser().getUsername());
        return PatientService.trasformToFHIRPatient(patient);
    }


    @Search()
    public ArrayList<Patient> searchPatientbyFamilyName(
            @RequiredParam(name = Patient.SP_FAMILY) StringParam familyName, @RequiredParam(name = Patient.SP_IDENTIFIER) TokenParam theId, HttpServletRequest request
    ) {


        String username = theId.getSystem();
        String token = theId.getValue();
        LogService.printLog(request.getRemoteAddr(), request.getRequestURL(), request.getMethod(), username);

        String role = authorize(token, username);

        //la lista di tutti i pazienti solo il medico puo vederla
        if (role != null) {
            if (role.equals("MEDIC")) {
                ArrayList<Patient> patientArrayList = new ArrayList<>();
                for (PatientEntity patient : PatientService.getAllPatients()) {
                    String fullname = patient.getPerson().getLastName().toLowerCase() + " " + patient.getPerson().getFirstName().toLowerCase();
                    if (familyName != null) {
                        if (fullname.contains(String.valueOf(familyName.getValueNotNull()).toLowerCase()))
                            patientArrayList.add(PatientService.trasformToFHIRPatient(patient));
                    }

                }
                return patientArrayList;
            }
        } else {
            OperationOutcome oo = new OperationOutcome();
            throw new InternalErrorException("Token is expired", oo);
        }
        return null;
    }
}
