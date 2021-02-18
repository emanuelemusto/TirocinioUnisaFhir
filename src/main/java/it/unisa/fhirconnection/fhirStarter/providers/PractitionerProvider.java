package it.unisa.fhirconnection.fhirStarter.providers;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.annotation.*;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.param.StringParam;
import ca.uhn.fhir.rest.param.TokenParam;
import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.exceptions.InternalErrorException;
import it.unisa.fhirconnection.fhirStarter.service.LogService;
import it.unisa.fhirconnection.fhirStarter.service.PractitionerService;
import it.unisa.fhirconnection.fhirStarter.model.PractitionerEntity;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import it.unisa.fhirconnection.fhirStarter.database.PractitionerDAO;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

import static it.unisa.fhirconnection.fhirStarter.service.UserService.authorize;

@Component
public class PractitionerProvider implements IResourceProvider {

    @Autowired
    FhirContext ctx;


    @Autowired
    private static PractitionerDAO practitionerDAO;


    private static final Logger log = LoggerFactory.getLogger(PractitionerProvider.class);

    @Override
    public Class<? extends IBaseResource> getResourceType() {
        return Practitioner.class;
    }

    @Read()
    public Practitioner readPractitioner(@IdParam IdType internalId, HttpServletRequest request) {
        LogService.printLog(request.getRemoteAddr(), request.getRequestURL(), request.getMethod(), internalId.getIdPart());
        PractitionerEntity practitioner = PractitionerService.getById(Integer.parseInt(internalId.getIdPart()));
        return PractitionerService.trasformToFHIRPractitioner(practitioner);
    }


    @Search()
    public ArrayList<Practitioner> searchPractitionerbyFamilyName(
            @RequiredParam(name = Practitioner.SP_FAMILY) StringParam familyName, @RequiredParam(name = Practitioner.SP_IDENTIFIER) TokenParam theId, HttpServletRequest request
    ) {

        String username = theId.getSystem();
        String token = theId.getValue();
        LogService.printLog(request.getRemoteAddr(), request.getRequestURL(), request.getMethod(), username);

        String role = authorize(token, username);

        if (role != null) {
            if (role.equals("PATIENT")) {
                ArrayList<Practitioner> practitionerArrayList = new ArrayList<>();
                for (PractitionerEntity practitioner : PractitionerService.getAllPractitioners()) {
                    String fullname = practitioner.getPerson().getLastName().toLowerCase() + " " + practitioner.getPerson().getFirstName().toLowerCase();
                    if (familyName != null) {
                        if (fullname.contains(String.valueOf(familyName.getValueNotNull()).toLowerCase()))
                            practitionerArrayList.add(PractitionerService.trasformToFHIRPractitioner(practitioner));
                    }

                }
                return practitionerArrayList;
            }
        } else {
            OperationOutcome oo = new OperationOutcome();
            throw new InternalErrorException("Token is expired", oo);
        }
        return null;
    }

}
