package enigma.application.mediator;

public interface Request<TResult> {
    /***
     * @return the type of TResult at runtime (erasure workaround)
     */
    Class<TResult> getResultType();
}
