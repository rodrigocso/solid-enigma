package enigma.application.mediator;

public abstract class RequestHandler<TRequest extends Request<TResult>, TResult> {
    /**
     * @return the type of TRequest at runtime (erasure workaround)
     */
    public abstract Class<TRequest> getRequestType();
    public abstract TResult handle(TRequest request);

    /**
     * @param request the captured request at runtime (with generic type information removed)
     * @return the result after ensuring the request type is correct
     */
    public TResult handleRequest(Request<?> request) {
        if (!getRequestType().isInstance(request)) {
            throw new ClassCastException(
                    "Unable to cast " + request.getClass() + " to " + getRequestType());
        }

        return handle(getRequestType().cast(request));
    }
}
