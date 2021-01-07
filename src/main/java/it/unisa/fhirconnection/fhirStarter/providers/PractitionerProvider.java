package it.unisa.fhirconnection.fhirStarter.providers;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.annotation.*;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.param.StringParam;
import ca.uhn.fhir.rest.server.IResourceProvider;
import it.unisa.fhirconnection.fhirStarter.service.PractitionerService;
import it.unisa.fhirconnection.fhirStarter.model.PractitionerEntity;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import it.unisa.fhirconnection.fhirStarter.database.PractitionerDAO;

import java.util.ArrayList;

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

   /* @Create
    public MethodOutcome createPractitioner(@ResourceParam Practitioner practitioner) {

        log.debug("Create Practitioner Provider called");

        MethodOutcome method = new MethodOutcome();
        method.setCreated(true);
        OperationOutcome opOutcome = new OperationOutcome();
        method.setOperationOutcome(opOutcome);

        try {
            PractitionerEntity practitionerEntity = fhirPractitionertoPractitionerEntity.transform(practitioner);
            practitionerDAO.save(practitionerEntity);
            log.info(String.valueOf(practitionerEntity.getId()));
            method.setId(practitioner.getIdElement());
            method.setResource(practitioner);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }

        log.debug("called create Practitioner method");

        return method;
    }*/

    /*@Read
    public ArrayList<PractitionerEntity> getAllPatitent(HttpServletRequest request) {
        for (PractitionerEntity p : practitionerDAO.findAll())
            practitionerEntityList.add(p);
        return practitionerEntityList;
    }*/

    @Read()
    public Practitioner readPractitioner(@IdParam IdType internalId) {
        PractitionerEntity practitioner = PractitionerService.getById(Integer.parseInt(internalId.getIdPart()));
        return PractitionerService.trasformToFHIRPractitioner(practitioner);
    }


    @Search()
    public ArrayList<Practitioner> searchPractitionerbyFamilyName(
            @RequiredParam(name = Practitioner.SP_FAMILY) StringParam familyName
    ) {
        ArrayList<Practitioner> practitionerArrayList = new ArrayList<>();
        for (PractitionerEntity practitioner : PractitionerService.getAllPractitioners()) {
            String fullname = practitioner.getPerson().getLastName().toLowerCase() + " " + practitioner.getPerson().getFirstName().toLowerCase();
            if (fullname.contains(String.valueOf(familyName.getValueNotNull()).toLowerCase()))
                practitionerArrayList.add(PractitionerService.trasformToFHIRPractitioner(practitioner));


        }
        return practitionerArrayList;
    }

    @Search()
    public ArrayList<Practitioner> searchPractitionerbyGivenName(
            @RequiredParam(name= Practitioner.SP_GIVEN) StringParam givenName
    ) {
        ArrayList<Practitioner> practitionerArrayList = new ArrayList<>();
        for (PractitionerEntity practitioner : PractitionerService.getAllPractitioners()) {
            if (practitioner.getPerson().getFirstName().toLowerCase().contains(String.valueOf(givenName.getValueNotNull()).toLowerCase()))
                practitionerArrayList.add(PractitionerService.trasformToFHIRPractitioner(practitioner));


        }
        return practitionerArrayList;
    }



}
