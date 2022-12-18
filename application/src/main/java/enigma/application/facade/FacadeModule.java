package enigma.application.facade;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import enigma.application.repository.UserRepository;

public class FacadeModule extends AbstractModule {
    @Provides
    static UserFacade provideUserFacade(UserRepository userRepository) {
        return new UserFacade(userRepository);
    }
}
