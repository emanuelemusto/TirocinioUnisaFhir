package it.unisa.fhirconnection.fhirStarter.RestController;

import it.unisa.fhirconnection.fhirStarter.service.DiagnosticReportService;
import it.unisa.fhirconnection.fhirStarter.service.LogService;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestController
@CrossOrigin(origins = "*")
public class MediaRestController {

    @RequestMapping(value = "uploadFile", method = RequestMethod.POST)
    public void uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) {

        DiagnosticReportService.uploadFile(file);

        System.out.println("message" +
                "You successfully uploaded " + file.getOriginalFilename() + "!");
        LogService.printLog(request.getRemoteAddr(),request.getRequestURL(),request.getMethod(), null);


    }

    @RequestMapping(value = "loadFile", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public static Resource reject(@ModelAttribute(value="path") String path, HttpServletRequest request) throws Exception {
        LogService.printLog(request.getRemoteAddr(),request.getRequestURL(),request.getMethod(), null);

        return DiagnosticReportService.loadAsResource(path);
    }
}