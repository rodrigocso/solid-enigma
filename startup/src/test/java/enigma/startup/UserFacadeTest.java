package enigma.startup;

import com.google.inject.Guice;
import com.google.inject.Injector;
import enigma.application.dto.ChangePasswordRequest;
import enigma.application.dto.ChangeUsernameRequest;
import enigma.application.dto.CreateUserRequest;
import enigma.application.dto.UserProfileResponse;
import enigma.application.exception.EntityNotFoundException;
import enigma.application.exception.VersionMismatchException;
import enigma.application.facade.FacadeModule;
import enigma.application.facade.UserFacade;
import enigma.domain.exception.IncorrectPasswordException;
import enigma.domain.exception.UsernameTakenException;
import enigma.infrastructure.repository.InMemoryRepositoriesModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class UserFacadeTest {
    private UserFacade sut;

    @BeforeEach
    void setup() {
        Injector injector = Guice.createInjector(
                new FacadeModule(),
                new InMemoryRepositoriesModule()
        );

        sut = injector.getInstance(UserFacade.class);
    }

    @Test
    void canCreateAndRetrieveUser() throws UsernameTakenException {
        String userId = sut.create(new CreateUserRequest("root", "1234"));
        UserProfileResponse user = sut.findById(userId).orElseThrow();

        assertThat(user.id()).isEqualTo(userId);
        assertThat(user.username()).isEqualTo("root");
    }

    @Test
    void cannotCreateUserWithDuplicatedUsername() throws UsernameTakenException {
        sut.create(new CreateUserRequest("root", "1234"));

        assertThatExceptionOfType(UsernameTakenException.class).isThrownBy(
                () -> sut.create(new CreateUserRequest("root", "4321")));
    }

    @Test
    void whenUserDoesNotExist_thenItFailsToRetrieveIt() {
        Optional<UserProfileResponse> userResult = sut.findById("non-existing id");
        assertThat(userResult).isEmpty();
    }

    @Test
    void givenUsernameIsNotTaken_thenUsernameCanBeChanged()
            throws EntityNotFoundException, UsernameTakenException, VersionMismatchException {
        String userId = sut.create(new CreateUserRequest("root", "1234"));
        UserProfileResponse user = sut.findById(userId).orElseThrow();

        sut.changeUsername(new ChangeUsernameRequest(user.id(), user.version(), "groot"));
        UserProfileResponse updatedUser = sut.findById(userId).orElseThrow();

        assertThat(updatedUser.username()).isEqualTo("groot");
    }

    @Test
    void cannotChangeUsernameToExistingUsername() throws UsernameTakenException {
        sut.create(new CreateUserRequest("root", "1234"));
        String userId = sut.create(new CreateUserRequest("bob", "secret"));

        UserProfileResponse user = sut.findById(userId).orElseThrow();
        var request = new ChangeUsernameRequest(user.id(), user.version(), "root");

        assertThatExceptionOfType(UsernameTakenException.class).isThrownBy(
                () -> sut.changeUsername(request));
    }

    @Test
    void givenCorrectCurrentPassword_thenPasswordCanBeChanged()
            throws UsernameTakenException, IncorrectPasswordException,
            EntityNotFoundException, VersionMismatchException {
        String userId = sut.create(new CreateUserRequest("root", "1234"));
        UserProfileResponse user = sut.findById(userId).orElseThrow();

        var request = new ChangePasswordRequest(
                user.id(), user.version(), "1234", "secret");
        sut.changePassword(request);

        var requestWithNewPassword = new ChangePasswordRequest(
                user.id(), user.version() + 1, "secret", "!@#$");
        sut.changePassword(requestWithNewPassword);
    }

    @Test
    void givenIncorrectCurrentPassword_whenChangingPassword_thenThrowIncorrectPasswordException()
            throws UsernameTakenException {
        String userId = sut.create(new CreateUserRequest("root", "1234"));
        UserProfileResponse user = sut.findById(userId).orElseThrow();

        var request = new ChangePasswordRequest(
                user.id(), user.version(), "wrongPassword", "secret");

        assertThatExceptionOfType(IncorrectPasswordException.class).isThrownBy(
                () -> sut.changePassword(request));
    }
}
