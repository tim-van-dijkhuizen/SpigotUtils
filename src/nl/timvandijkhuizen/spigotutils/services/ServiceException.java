package nl.timvandijkhuizen.spigotutils.services;

public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 1992580686756434407L;

    public ServiceException(String message) {
        super(message);
    }

}
