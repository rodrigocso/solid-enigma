package enigma.application.facade;

import com.google.inject.Inject;
import enigma.application.dto.ChangePasswordRequest;
import enigma.application.dto.ChangeUsernameRequest;
import enigma.application.dto.CreateUserRequest;
import enigma.application.dto.UserProfileResponse;
import enigma.application.exception.EntityNotFoundException;
import enigma.application.exception.VersionMismatchException;
import enigma.application.repository.UserRepository;
import enigma.domain.entity.User;
import enigma.domain.exception.IncorrectPasswordException;
import enigma.domain.exception.UsernameTakenException;

import java.util.Optional;
import java.util.UUID;

public class UserFacade {
    private final UserRepository userRepository;

    @Inject
    UserFacade(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String create(CreateUserRequest request) throws UsernameTakenException {
        if (userRepository.findByUsername(request.username()).isPresent()) {
            throw new UsernameTakenException();
        }

        var user = new User.Builder(UUID.randomUUID().toString())
                .username(request.username())
                .password(request.password())
                .build();

        userRepository.create(user);

        return user.getId();
    }

    public Optional<UserProfileResponse> findById(String id) {
        return userRepository.findById(id)
                .map(UserProfileResponse::from);
    }

    public void changeUsername(ChangeUsernameRequest request)
            throws EntityNotFoundException, UsernameTakenException, VersionMismatchException {
        User user = userRepository.findById(request.userId())
                .orElseThrow(EntityNotFoundException::new);

        if (userRepository.findByUsername(request.newUsername()).isPresent()) {
            throw new UsernameTakenException();
        }

        user.setUsername(request.newUsername());
        userRepository.update(user);
    }

    public void changePassword(ChangePasswordRequest request)
            throws EntityNotFoundException, IncorrectPasswordException, VersionMismatchException {
        User user = userRepository.findById(request.userId())
                .orElseThrow(EntityNotFoundException::new);

        if (!user.verifyPassword(request.currentPassword())) {
            throw new IncorrectPasswordException();
        }

        user.setPassword(request.newPassword());
        userRepository.update(user);
    }
}
