package enigma.user.application.service;

import com.google.inject.Inject;
import enigma.common.mediator.RequestHandler;
import enigma.user.application.dto.query.GetUsersQuery;
import enigma.user.application.dto.response.GetUsersResponse;
import enigma.user.application.persistence.UserRepository;
import enigma.user.domain.entity.User;

import java.util.stream.Collectors;

public class GetUsersQueryHandler extends RequestHandler<GetUsersQuery, GetUsersResponse> {
    private final UserRepository userRepository;

    @Inject
    public GetUsersQueryHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public GetUsersResponse handle(GetUsersQuery request) {
        return new GetUsersResponse(userRepository.getUsers()
                .stream()
                .map(User::getUsername)
                .collect(Collectors.toList()));
    }
}
