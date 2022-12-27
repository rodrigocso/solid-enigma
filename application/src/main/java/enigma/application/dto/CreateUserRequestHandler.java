package enigma.application.dto;

import com.google.inject.Inject;
import enigma.application.mediator.RequestHandler;
import enigma.application.repository.UserRepository;
import enigma.domain.entity.User;

import java.util.UUID;

public class CreateUserRequestHandler extends RequestHandler<CreateUserRequest, String> {
    private final UserRepository userRepository;

    @Inject
    public CreateUserRequestHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Class<CreateUserRequest> getRequestType() {
        return CreateUserRequest.class;
    }

    @Override
    public String handle(CreateUserRequest request) {
        var user = new User.Builder(UUID.randomUUID().toString())
                .username(request.username())
                .password(request.password())
                .build();

        userRepository.create(user);

        return user.getId();
    }
}
