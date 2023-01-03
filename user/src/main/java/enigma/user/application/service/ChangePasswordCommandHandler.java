package enigma.user.application.service;

import com.google.inject.Inject;
import enigma.common.mediator.RequestHandler;
import enigma.user.application.dto.command.ChangePasswordCommand;
import enigma.user.application.exception.EntityNotFoundException;
import enigma.user.application.exception.VersionMismatchException;
import enigma.user.application.persistence.UserRepository;
import enigma.user.domain.entity.User;
import enigma.user.domain.exception.IncorrectPasswordException;

public class ChangePasswordCommandHandler extends RequestHandler<ChangePasswordCommand, Void> {
    private final UserRepository userRepository;

    @Inject
    public ChangePasswordCommandHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Void handle(ChangePasswordCommand command)
            throws EntityNotFoundException, IncorrectPasswordException, VersionMismatchException {
        User user = userRepository.findById(command.userId())
                .orElseThrow(EntityNotFoundException::new);

        if (!user.verifyPassword(command.currentPassword())) {
            throw new IncorrectPasswordException();
        }

        user.setPassword(command.newPassword());
        userRepository.update(user);

        return null;
    }
}
