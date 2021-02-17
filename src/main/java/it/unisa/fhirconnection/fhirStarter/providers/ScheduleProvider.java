package it.unisa.fhirconnection.fhirStarter.providers;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.annotation.*;
import ca.uhn.fhir.rest.param.StringParam;
import ca.uhn.fhir.rest.server.IResourceProvider;
import it.unisa.fhirconnection.fhirStarter.database.ScheduleDAO;
import it.unisa.fhirconnection.fhirStarter.model.PatientEntity;
import it.unisa.fhirconnection.fhirStarter.model.PractitionerEntity;
import it.unisa.fhirconnection.fhirStarter.service.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Component
public class ScheduleProvider implements IResourceProvider {

    @Autowired
    FhirContext ctx;


    @Autowired
    private static ScheduleDAO scheduleDAO;


    private static final Logger log = LoggerFactory.getLogger(ScheduleProvider.class);

    @Override
    public Class<? extends IBaseResource> getResourceType() {
        return Schedule.class;
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
    public Schedule readSchedule(@IdParam IdType internalId) {
        it.unisa.fhirconnection.fhirStarter.model.Schedule schedule = ScheduleService.getById(Integer.parseInt(internalId.getIdPart()));
        return ScheduleService.transform(schedule);
    }


    @Search()
    public ArrayList<Schedule> getAllbyPatient(@RequiredParam(name = Schedule.SP_RES_ID) StringParam id, HttpServletRequest request) {
        PatientEntity patient = PatientService.getById(Integer.parseInt(String.valueOf(id.getValueNotNull())));
        ArrayList<Schedule> schedules = new ArrayList<>();
        LogService.printLog(request.getRemoteAddr(),request.getRequestURL(),request.getMethod(),null);

        for (it.unisa.fhirconnection.fhirStarter.model.Schedule schedule : patient.getSchedules()) {
            schedules.add(ScheduleService.transform(schedule));
        }

        return schedules;
    }

    @Search()
    public ArrayList<Schedule> getAllbyPractionier(@RequiredParam(name = Schedule.SP_ACTOR) StringParam id,HttpServletRequest request) {
        PractitionerEntity practitionerEntity = PractitionerService.getById(Integer.parseInt(String.valueOf(id.getValueNotNull())));
        ArrayList<Schedule> schedules = new ArrayList<>();
        LogService.printLog(request.getRemoteAddr(),request.getRequestURL(),request.getMethod(),null);

        for (it.unisa.fhirconnection.fhirStarter.model.Schedule schedule : practitionerEntity.getSchedules()) {
            schedules.add(ScheduleService.transform(schedule));
        }

        return schedules;
    }
}
