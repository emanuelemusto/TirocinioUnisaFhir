package it.unisa.fhirconnection.fhirStarter.service;

import it.unisa.fhirconnection.fhirStarter.exception.StorageFileNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Questa classe implementa i metodi  per la logica di business del sottosistema "Documento"
 */
@Service
@RequiredArgsConstructor
public class MediaService {

    private static String BASE_PATH = "src/main/resources/documents/";


    private String storeFile(final byte[] bytes, final String fileName) {
        String first = BASE_PATH + System.currentTimeMillis() + "-" + fileName;
        Path path = Paths.get(first);
        try {
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return first;
    }

    public Resource loadAsResource(final String path) {
        try {
            Resource resource = new UrlResource(Paths.get(path).toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException("Could not read file: " + path);
            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + path, e);
        }
    }
}