package enigma.infrastructure.repository;

import java.util.HashMap;
import java.util.Optional;

import com.google.inject.Singleton;

import enigma.application.exception.EntityNotFoundException;
import enigma.application.exception.VersionMismatchException;
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
    public Optional<User> findByUsername(String username) {
        return usersData.values().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst();
    }

    @Override
    public void create(User user) {
        usersData.put(user.getId(), user);
    }

    @Override
    public void update(User user) throws EntityNotFoundException, VersionMismatchException {
        if (!usersData.containsKey(user.getId())) {
            throw new EntityNotFoundException();
        }

        if (user.getVersion() != usersData.get(user.getId()).getVersion()) {
            throw new VersionMismatchException();
        }

        user.incrementVersion();
        usersData.put(user.getId(), user);
    }
}
