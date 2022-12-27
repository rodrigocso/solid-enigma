package enigma.application.mediator;

import java.util.concurrent.CompletableFuture;

public interface Mediator {
    <TResult> CompletableFuture<TResult> executeQueryAsync(Request<TResult> request);
}
