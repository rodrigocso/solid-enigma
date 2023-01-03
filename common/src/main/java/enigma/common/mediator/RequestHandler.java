package enigma.common.mediator;

public abstract class RequestHandler<TRequest, TResult> {
    public abstract TResult handle(TRequest request) throws Exception;

    @SuppressWarnings("unchecked")
    public TResult handleRequest(Request<?> request) throws Exception {
        return handle((TRequest) request);
    }
}
