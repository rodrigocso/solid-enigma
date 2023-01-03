package enigma.common.mediator;

import java.util.concurrent.CompletableFuture;

public interface Mediator {
    <TResult> CompletableFuture<TResult> send(Request<TResult> request);
}
