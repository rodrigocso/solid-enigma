package enigma.common.mediator.simple;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import enigma.common.mediator.Request;
import enigma.common.mediator.RequestHandler;
import enigma.common.mediator.RequestHandlerLoader;
import enigma.common.mediator.exception.RequestHandlerConstructorException;
import enigma.common.mediator.exception.RequestHandlerInstantiationException;
import enigma.common.mediator.exception.RequestHandlerNotFoundException;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;

@Singleton
public class ReflectionRequestHandlerLoader implements RequestHandlerLoader {
    private final Injector injector;
    private String[] packagesToScan;

    @Inject
    public ReflectionRequestHandlerLoader(Injector injector) {
        this.injector = injector;
    }

    public void setPackagesToScan(String[] packagesToScan) {
        this.packagesToScan = packagesToScan;
    }

    @Override
    public RequestHandler<?, ?> loadRequestHandler(Request<?> request)
            throws RequestHandlerConstructorException, RequestHandlerInstantiationException, RequestHandlerNotFoundException {
        var requestHandler = new Reflections(new ConfigurationBuilder()
                .forPackages(packagesToScan))
                .getSubTypesOf(RequestHandler.class)
                .stream()
                .filter(c -> !Modifier.isAbstract(c.getModifiers()) &&
                        ((ParameterizedType) c.getGenericSuperclass())
                                .getActualTypeArguments()[0]
                                .equals(request.getClass()))
                .findFirst()
                .orElseThrow(RequestHandlerNotFoundException::new);

        var constructors = requestHandler.getConstructors();

        if (constructors.length == 0) {
            throw new RequestHandlerConstructorException(requestHandler);
        }

        Object[] params = Arrays.stream(constructors[0].getParameterTypes())
                .map(injector::getInstance)
                .toArray();

        try {
            return (RequestHandler<?, ?>) constructors[0].newInstance(params);
        } catch (Exception e) {
            throw new RequestHandlerInstantiationException(requestHandler, e);
        }
    }
}
