package enigma.application.facade;

import com.google.inject.Inject;
import enigma.application.repository.UserRepository;
import enigma.domain.entity.User;

import java.util.Optional;

public class UserFacade {
    private final UserRepository userRepository;

    @Inject
    UserFacade(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String create(String username, String password) {
        var user = new User();
        user.setUsername(username);
        user.setPassword(password);

        userRepository.create(user);

        return user.getId();
    }

    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }
}
