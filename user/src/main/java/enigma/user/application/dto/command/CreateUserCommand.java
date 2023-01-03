package enigma.user.application.dto.command;

import enigma.common.mediator.Request;
import enigma.user.application.dto.response.CreateUserResponse;

public record CreateUserCommand(String username, String password)
        implements Request<CreateUserResponse> {
}
