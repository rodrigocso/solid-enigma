package enigma.common.mediator.exception;

public class RequestHandlerConstructorException extends Exception {
    private final Class<?> requestHandlerClass;

    public RequestHandlerConstructorException(Class<?> requestHandlerClass) {
        this.requestHandlerClass = requestHandlerClass;
    }

    @Override
    public String toString() {
        return String.format("Could not find a public constructor for %s", requestHandlerClass);
    }
}
