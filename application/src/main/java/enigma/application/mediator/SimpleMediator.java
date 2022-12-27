package enigma.application.mediator;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class SimpleMediator implements Mediator {
    private final ConcurrentHashMap<Class<?>, RequestHandler<? extends Request<?>, ?>>
            handlersMap = new ConcurrentHashMap<>();

    @Inject
    public SimpleMediator(Injector injector) {
        registerHandlers(injector);
    }

    // Danger zone!
    private void registerHandlers(Injector injector) {
        var reflections = new Reflections(new ConfigurationBuilder()
                .forPackage("enigma"));

        reflections.getSubTypesOf(RequestHandler.class)
                .stream()
                .filter(c -> !Modifier.isAbstract(c.getModifiers()))
                .forEach(c -> {
                    var constructor = Arrays.stream(c.getConstructors())
                            .findFirst()
                            .orElseThrow();

                    Object[] params = Arrays.stream(constructor.getParameterTypes())
                            .map(injector::getInstance)
                            .toArray();

                    try {
                        var instance = constructor.newInstance(params);
                        registerHandler((RequestHandler<? extends Request<?>, ?>) instance);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    private void registerHandler(RequestHandler<? extends Request<?>, ?> requestHandler) {
        if (handlersMap.containsKey(requestHandler.getRequestType())) {
            throw new IllegalArgumentException(
                    "There is already a handler for " + requestHandler.getRequestType());
        }

        handlersMap.put(requestHandler.getRequestType(), requestHandler);
    }

    @Override
    public <TResult> CompletableFuture<TResult> executeQueryAsync(Request<TResult> request) {
        if (!handlersMap.containsKey(request.getClass())) {
            return CompletableFuture.failedFuture(new IllegalArgumentException(
                    "Could not find registered handler for " + request.getClass()));
        }

        return CompletableFuture
                .supplyAsync(() -> {
                    var handler = handlersMap.get(request.getClass());
                    var resultType = request.getResultType();
                    var requestType = handler.getRequestType();

                    return resultType.cast(handler.handleRequest(requestType.cast(request)));
                });
    }
}
