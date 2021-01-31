package it.unisa.fhirconnection.fhirStarter;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.context.FhirVersionEnum;
import ca.uhn.fhir.rest.api.EncodingEnum;
import ca.uhn.fhir.rest.server.FifoMemoryPagingProvider;
import ca.uhn.fhir.rest.server.HardcodedServerAddressStrategy;
import ca.uhn.fhir.rest.server.RestfulServer;
import ca.uhn.fhir.rest.server.interceptor.IServerInterceptor;
import ca.uhn.fhir.rest.server.interceptor.ResponseHighlighterInterceptor;
import ca.uhn.fhir.util.VersionUtil;
import it.unisa.fhirconnection.fhirStarter.providers.*;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.TimeZone;


public class fhirStarterRestfulServer extends RestfulServer {

	private static final long serialVersionUID = 1L;
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(fhirStarterRestfulServer.class);

	private ApplicationContext applicationContext;

	fhirStarterRestfulServer(ApplicationContext context) {
		this.applicationContext = context;
	}

	@Value("http://127.0.0.1/STU3")
	private String serverBase;


	@Override
	public void addHeadersToResponse(HttpServletResponse theHttpResponse) {
		theHttpResponse.addHeader("X-Powered-By", "HAPI FHIR " + VersionUtil.getVersion() + " RESTful Server (INTEROPen Care Connect STU3)");
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void initialize() throws ServletException {
		super.initialize();
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

		FhirVersionEnum fhirVersion = FhirVersionEnum.DSTU3;
		setFhirContext(new FhirContext(fhirVersion));

		if (serverBase != null && !serverBase.isEmpty()) {
			setServerAddressStrategy(new HardcodedServerAddressStrategy(serverBase));
		}

		setResourceProviders(Arrays.asList(
				applicationContext.getBean(PatientProvider.class),
				applicationContext.getBean(DiagnosticReportProvider.class),
				applicationContext.getBean(AllergyIntoleranceProvider.class),
				applicationContext.getBean(PractitionerProvider.class),
				applicationContext.getBean(ProblemProvider.class),
				applicationContext.getBean(MedicationProvider.class),
				applicationContext.getBean(ScheduleProvider.class)
		));


		FifoMemoryPagingProvider pp = new FifoMemoryPagingProvider(10000);
		pp.setDefaultPageSize(10000);
		pp.setMaximumPageSize(10000);
		setPagingProvider(pp);

		setDefaultPrettyPrint(true);
		setDefaultResponseEncoding(EncodingEnum.JSON);

	}

	/**
	 * This interceptor adds some pretty syntax highlighting in responses when a browser is detected
	 */
	@Bean(autowire = Autowire.BY_TYPE)
	public IServerInterceptor responseHighlighterInterceptor() {
		ResponseHighlighterInterceptor retVal = new ResponseHighlighterInterceptor();
		return retVal;
	}

}
