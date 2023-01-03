package enigma.common;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import enigma.common.mediator.RequestHandlerLoader;
import enigma.common.mediator.Mediator;
import enigma.common.mediator.simple.ReflectionRequestHandlerLoader;
import enigma.common.mediator.simple.SimpleMediator;

public class CommonModule extends AbstractModule {
    private final String[] packagesWithRequestHandlers;

    public CommonModule(String... packagesWithRequestHandlers) {
        this.packagesWithRequestHandlers = packagesWithRequestHandlers;
    }

    @Provides
    RequestHandlerLoader provideRequestHandlerLoader(ReflectionRequestHandlerLoader requestHandlerLoader) {
        requestHandlerLoader.setPackagesToScan(packagesWithRequestHandlers);
        return requestHandlerLoader;
    }

    @Provides
    Mediator provideMediator(SimpleMediator simpleMediator) {
        return simpleMediator;
    }
}
