package it.unisa.fhirconnection.fhirStarter.exception;

public class StorageException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private String msg;

    public StorageException(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}