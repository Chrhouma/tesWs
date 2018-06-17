package com.pictimegroupe.FrontVendeur.testWebservice.Exception;

public class GestionRoleException extends Exception {
    public GestionRoleException() {
    }

    public GestionRoleException(String message) {
        super(message);
    }

    public GestionRoleException(String message, Throwable cause) {
        super(message, cause);
    }

    public GestionRoleException(Throwable cause) {
        super(cause);
    }

    public GestionRoleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
