package enigma.user.application.service;

import com.google.inject.Guice;
import com.google.inject.Injector;
import enigma.common.CommonModule;
import enigma.common.mediator.Mediator;
import enigma.user.UserModule;
import enigma.user.application.dto.command.CreateUserCommand;
import enigma.user.application.dto.response.CreateUserResponse;
import enigma.user.application.persistence.UserRepository;
import enigma.user.domain.entity.User;
import enigma.user.domain.exception.UsernameTakenException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletionException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

public class CreateUserCommandTests {
    private Mediator mediator;
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        Injector injector = Guice.createInjector(
                new CommonModule("enigma.user.application.service"),
                new UserModule()
        );

        mediator = injector.getInstance(Mediator.class);
        userRepository = injector.getInstance(UserRepository.class);
    }

    @Test
    void canCreateUser() {
        var command = new CreateUserCommand("root", "1234");
        CreateUserResponse response = mediator.send(command).join();
        User user = userRepository.findById(response.userId()).orElseThrow();

        assertThat(user.getUsername()).isEqualTo("root");
        assertThat(user.verifyPassword("1234")).isTrue();
    }

    @Test
    void givenUsernameIsTaken_whenCreateUser_thenThrowUsernameTakenException() throws Exception {
        userRepository.save(new User.Builder("user_id").username("root").build());

        var command = new CreateUserCommand("root", "1234");
        Throwable thrown = catchThrowable(() -> mediator.send(command).join());

        assertThat(thrown).isInstanceOfSatisfying(
                CompletionException.class,
                ex -> assertThat(ex.getCause()).isInstanceOf(UsernameTakenException.class));
    }
}
