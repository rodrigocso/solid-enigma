package enigma.user.application.service;

import com.google.inject.Guice;
import com.google.inject.Injector;
import enigma.common.CommonModule;
import enigma.common.mediator.Mediator;
import enigma.user.UserModule;
import enigma.user.application.dto.command.ChangePasswordCommand;
import enigma.user.application.persistence.UserRepository;
import enigma.user.domain.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ChangePasswordCommandTests {
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
    void canChangePassword_whenCurrentPasswordIsVerified() throws Exception {
        User user = new User.Builder("user-id")
                .username("bob")
                .password("1234")
                .build();

        userRepository.save(user);

        var command = new ChangePasswordCommand(
                user.getId(), user.getVersion(), "1234", "4321");

        mediator.send(command);

        assertThat(userRepository.findById("user-id")
                .orElseThrow()
                .verifyPassword("1234")).isTrue();
    }
}
