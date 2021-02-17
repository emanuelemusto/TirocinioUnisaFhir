package it.unisa.fhirconnection.fhirStarter.providers;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.annotation.*;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.param.StringParam;
import ca.uhn.fhir.rest.param.TokenParam;
import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.exceptions.InternalErrorException;
import it.unisa.fhirconnection.fhirStarter.model.PatientEntity;
import it.unisa.fhirconnection.fhirStarter.model.Problem;
import it.unisa.fhirconnection.fhirStarter.service.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

import static it.unisa.fhirconnection.fhirStarter.service.UserService.authorizeByPatientId;

@Component
public class ProblemProvider implements IResourceProvider {

    @Autowired
    FhirContext ctx;


    private static final Logger log = LoggerFactory.getLogger(ProblemProvider.class);

    @Override
    public Class<? extends IBaseResource> getResourceType() {
        return Condition.class;
    }

    @Create
    public MethodOutcome create(@ResourceParam Condition condition) {

        log.debug("Create DiagnosticReport Provider called");

        MethodOutcome method = new MethodOutcome();
        method.setCreated(true);
        OperationOutcome opOutcome = new OperationOutcome();
        method.setOperationOutcome(opOutcome);


        log.debug("called create DiagnosticReport method");

        return method;
    }

    @Search()
    public ArrayList<Condition> getAllbyPatient(@RequiredParam(name = Condition.SP_RES_ID) StringParam id, @RequiredParam(name = Patient.SP_IDENTIFIER) TokenParam theId, HttpServletRequest request) {
        String username = theId.getSystem();
        String token = theId.getValue();
        LogService.printLog(request.getRemoteAddr(), request.getRequestURL(), request.getMethod(), username);


        if (authorizeByPatientId(token, username, Integer.parseInt(String.valueOf(id.getValueNotNull())))) {
            PatientEntity patient = PatientService.getById(Integer.parseInt(String.valueOf(id.getValueNotNull())));
            ArrayList<Condition> conditions = new ArrayList<>();
            for (it.unisa.fhirconnection.fhirStarter.model.Problem problem : patient.getProblems()) {
                conditions.add(ProblemService.transform(problem));
            }
            return conditions;
        } else {
            OperationOutcome oo = new OperationOutcome();
            throw new InternalErrorException("Token is expired", oo);
        }
    }


}
