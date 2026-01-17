package pe.jsaire.springtravel.utils.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s NOT FOUND %s : '%s'", resourceName, fieldName, fieldValue));
    }
}
