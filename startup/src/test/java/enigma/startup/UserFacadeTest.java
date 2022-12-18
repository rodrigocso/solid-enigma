package enigma.startup;

import com.google.inject.Guice;
import com.google.inject.Injector;
import enigma.application.facade.FacadeModule;
import enigma.application.facade.UserFacade;
import enigma.domain.entity.User;
import enigma.infrastructure.repository.InMemoryRepositoriesModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserFacadeTest {
    private static UserFacade sut;

    @BeforeEach
    void setup() {
        Injector injector = Guice.createInjector(
                new FacadeModule(),
                new InMemoryRepositoriesModule()
        );

        sut = injector.getInstance(UserFacade.class);
    }

    @Test
    void canCreateAndRetrieveUser() {
        String userId = sut.create("root", "1234");
        Optional<User> user = sut.findById(userId);

        assertTrue(user.isPresent());
        assertEquals(user.get().getId(), userId);
        assertEquals(user.get().getUsername(), "root");
        assertEquals(user.get().getPassword(), "1234");
    }

    @Test
    void whenUserDoesNotExistThenItFailsToRetrieveIt() {
        Optional<User> user = sut.findById("non-existing id");
        assertTrue(user.isEmpty());
    }
}
