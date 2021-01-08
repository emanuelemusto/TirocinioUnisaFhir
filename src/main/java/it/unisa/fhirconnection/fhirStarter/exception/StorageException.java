package it.unisa.fhirconnection.fhirStarter.exception;

public class StorageException extends RuntimeException {
    public StorageException(final String message) {
        super(message);
    }

    public StorageException(final String message, final Throwable cause) {
        super(message, cause);
    }
}