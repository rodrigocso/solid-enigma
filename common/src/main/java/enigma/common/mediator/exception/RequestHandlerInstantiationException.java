package enigma.common.mediator.exception;

public class RequestHandlerInstantiationException extends Exception {
    private final Class<?> requestHandlerClass;
    private final Exception innerException;

    public RequestHandlerInstantiationException(Class<?> requestHandlerClass, Exception innerException) {
        this.requestHandlerClass = requestHandlerClass;
        this.innerException = innerException;
    }

    @Override
    public String toString() {
        return String.format("Could not instantiate %s: %s", requestHandlerClass, innerException);
    }
}
