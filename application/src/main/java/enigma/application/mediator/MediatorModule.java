package enigma.application.mediator;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class MediatorModule extends AbstractModule {
    @Provides
    static Mediator provideMediator(SimpleMediator simpleMediator) {
        return simpleMediator;
    }
}
