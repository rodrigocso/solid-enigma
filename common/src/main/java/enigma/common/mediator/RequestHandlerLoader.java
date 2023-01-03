package enigma.common.mediator;

public interface RequestHandlerLoader {
    RequestHandler<?, ?> loadRequestHandler(Request<?> request) throws Exception;
}
