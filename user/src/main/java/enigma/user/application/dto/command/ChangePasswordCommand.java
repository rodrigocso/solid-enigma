package enigma.user.application.dto.command;

import enigma.common.mediator.Request;

public record ChangePasswordCommand(String userId, int version, String currentPassword, String newPassword)
        implements Request<Void> {
}
