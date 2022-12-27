package enigma.application.dto;

import enigma.application.mediator.Request;

public record CreateUserRequest(String username, String password) implements Request<String> {
    @Override
    public Class<String> getResultType() {
        return String.class;
    }
}
