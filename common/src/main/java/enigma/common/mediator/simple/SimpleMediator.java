package enigma.common.mediator.simple;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import enigma.common.mediator.Mediator;
import enigma.common.mediator.Request;
import enigma.common.mediator.RequestHandler;
import enigma.common.mediator.RequestHandlerLoader;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class SimpleMediator implements Mediator {
    private final ConcurrentHashMap<Class<?>, RequestHandler<?, ?>>
            handlersMap = new ConcurrentHashMap<>();
    private final RequestHandlerLoader requestHandlerLoader;

    @Inject
    public SimpleMediator(RequestHandlerLoader requestHandlerLoader) {
        this.requestHandlerLoader = requestHandlerLoader;
    }

    @Override
    public <TResponse> CompletableFuture<TResponse> send(Request<TResponse> request) {
        if (!handlersMap.containsKey(request.getClass())) {
            try {
                handlersMap.putIfAbsent(request.getClass(),
                        requestHandlerLoader.loadRequestHandler(request));
            } catch (Exception e) {
                return CompletableFuture.failedFuture(e);
            }
        }

        return CompletableFuture.supplyAsync(() -> {
            var handler = handlersMap.get(request.getClass());

            try {
                //noinspection unchecked
                return (TResponse) handler.handleRequest(request);
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        });
    }
}
