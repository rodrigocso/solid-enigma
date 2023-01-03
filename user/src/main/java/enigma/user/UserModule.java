package enigma.user;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import enigma.user.application.persistence.UserRepository;
import enigma.user.infrastructure.persistence.InMemoryUserRepository;

public class UserModule extends AbstractModule {
    @Provides
    static UserRepository provideUserRepository(InMemoryUserRepository inMemoryUserRepository) {
        return inMemoryUserRepository;
    }
}
