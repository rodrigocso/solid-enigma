package enigma.infrastructure.repository;

import java.util.HashMap;
import java.util.Optional;

import com.google.inject.Singleton;

import enigma.application.repository.UserRepository;
import enigma.domain.entity.User;

@Singleton
public class InMemoryUserRepository implements UserRepository {
    private final HashMap<String, User> usersData = new HashMap<>();

    @Override
    public Optional<User> findById(String id) {
        return Optional.ofNullable(usersData.get(id));
    }

    @Override
    public void create(User user) {
        usersData.put(user.getId(), user);
    }
}
