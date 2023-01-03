package enigma.user.application.service;

import com.google.inject.Inject;
import enigma.common.mediator.RequestHandler;
import enigma.user.application.dto.command.CreateUserCommand;
import enigma.user.application.dto.response.CreateUserResponse;
import enigma.user.application.exception.VersionMismatchException;
import enigma.user.application.persistence.UserRepository;
import enigma.user.domain.entity.User;
import enigma.user.domain.exception.UsernameTakenException;

import java.util.UUID;

public class CreateUserCommandHandler extends RequestHandler<CreateUserCommand, CreateUserResponse> {
    private final UserRepository userRepository;

    @Inject
    public CreateUserCommandHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public CreateUserResponse handle(CreateUserCommand command)
            throws UsernameTakenException, VersionMismatchException {
        // concurrent access may still result in duplicated usernames
        if (userRepository.findByUsername(command.username()).isPresent()) {
            throw new UsernameTakenException();
        }

        User user = new User.Builder(UUID.randomUUID().toString())
                .username(command.username())
                .password(command.password())
                .build();

        userRepository.save(user);

        return new CreateUserResponse(user.getId());
    }
}
