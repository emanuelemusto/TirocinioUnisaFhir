package it.unisa.fhirconnection.fhirStarter.providers;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.annotation.*;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.param.StringParam;
import ca.uhn.fhir.rest.param.TokenParam;
import ca.uhn.fhir.rest.server.IResourceProvider;
import it.unisa.fhirconnection.fhirStarter.service.AllergyIntoleranceService;
import it.unisa.fhirconnection.fhirStarter.service.DiagnosticReportService;
import it.unisa.fhirconnection.fhirStarter.service.PatientService;
import it.unisa.fhirconnection.fhirStarter.model.PatientEntity;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

import static it.unisa.fhirconnection.fhirStarter.service.UserService.authorize;
import static it.unisa.fhirconnection.fhirStarter.service.UserService.authorizeByPatientId;

@Component
public class AllergyIntoleranceProvider implements IResourceProvider {

    @Autowired
    FhirContext ctx;


    private static final Logger log = LoggerFactory.getLogger(AllergyIntoleranceProvider.class);

    @Override
    public Class<? extends IBaseResource> getResourceType() {
        return AllergyIntolerance.class;
    }

    @Create
    public MethodOutcome create(@ResourceParam AllergyIntolerance allergyIntolerance) {

        log.debug("Create DiagnosticReport Provider called");

        MethodOutcome method = new MethodOutcome();
        method.setCreated(true);
        OperationOutcome opOutcome = new OperationOutcome();
        method.setOperationOutcome(opOutcome);


        log.debug("called create DiagnosticReport method");

        return method;
    }


    @Read()
    public ArrayList<AllergyIntolerance> getAllbyPatient(@IdParam IdType internalId) {
        PatientEntity patient = PatientService.getById(Integer.parseInt(internalId.getIdPart()));
        ArrayList<AllergyIntolerance> allergyIntolerances = new ArrayList<>();

        for (it.unisa.fhirconnection.fhirStarter.model.AllergyIntolerance allergyIntolerance : patient.getAllergyIntolerances()) {
            allergyIntolerances.add(AllergyIntoleranceService.transform(allergyIntolerance));


        }

        return allergyIntolerances;
    }

    @Search()
    public ArrayList<AllergyIntolerance> getAllbyPatient(@RequiredParam(name = AllergyIntolerance.SP_RES_ID) StringParam id,@RequiredParam(name=Patient.SP_IDENTIFIER) TokenParam theId) {
        System.out.println("prova token value "+theId.getValue());
        System.out.println("prova token System "+theId.getSystem());
        String username = theId.getSystem();
        String token = theId.getValue();

        String role = authorize(token,username);
       if(authorizeByPatientId(token,username,Integer.parseInt(String.valueOf(id.getValueNotNull())))){
           System.out.println("prova entarta ");
        PatientEntity patient = PatientService.getById(Integer.parseInt(String.valueOf(id.getValueNotNull())));
        ArrayList<AllergyIntolerance> allergyIntolerances = new ArrayList<>();

        for (it.unisa.fhirconnection.fhirStarter.model.AllergyIntolerance allergyIntolerance : patient.getAllergyIntolerances()) {
            allergyIntolerances.add(AllergyIntoleranceService.transform(allergyIntolerance));


        }

        return allergyIntolerances;}

        return null;
    }




}
