package enigma.infrastructure.repository;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import enigma.application.repository.UserRepository;

public class InMemoryRepositoriesModule extends AbstractModule {
    @Provides
    static UserRepository provideUserRepository() {
        return new InMemoryUserRepository();
    }
}
