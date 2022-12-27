package enigma.startup;

import com.google.inject.Guice;
import com.google.inject.Injector;
import enigma.application.dto.CreateUserRequest;
import enigma.application.dto.CreateUserRequestHandler;
import enigma.application.mediator.Mediator;
import enigma.application.mediator.MediatorModule;
import enigma.application.repository.UserRepository;
import enigma.domain.entity.User;
import enigma.infrastructure.repository.InMemoryRepositoriesModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateUserTests {
    private Injector injector;
    private Mediator mediator;
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        injector = Guice.createInjector(
                new MediatorModule(),
                new InMemoryRepositoriesModule()
        );

        mediator = injector.getInstance(Mediator.class);
        userRepository = injector.getInstance(UserRepository.class);
    }

    @Test
    public void canCreateUser() throws ExecutionException, InterruptedException {
        String userId = mediator.executeQueryAsync(
                new CreateUserRequest("root", "1234")).get();

        User user = userRepository.findById(userId).orElseThrow();

        assertThat(user.getId()).isEqualTo(userId);
        assertThat(user.getUsername()).isEqualTo("root");
    }

    @Test
    public void createInstance() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        var constructor = Arrays.stream(CreateUserRequestHandler.class.getConstructors())
                .findFirst()
                .orElseThrow();

        Object[] params = Arrays.stream(constructor.getParameterTypes())
                .map(injector::getInstance)
                .toArray();

        var instance = constructor.newInstance(params);
        assertThat(instance).isNotNull();
    }
}
