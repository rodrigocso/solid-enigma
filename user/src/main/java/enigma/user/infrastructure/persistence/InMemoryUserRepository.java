package enigma.user.infrastructure.persistence;

import com.google.inject.Singleton;
import enigma.user.application.exception.EntityNotFoundException;
import enigma.user.application.exception.VersionMismatchException;
import enigma.user.application.persistence.UserRepository;
import enigma.user.domain.entity.User;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class InMemoryUserRepository implements UserRepository {
    private final ConcurrentHashMap<String, User> usersData = new ConcurrentHashMap<>();

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
    public void save(User user) throws VersionMismatchException {
        if (usersData.containsKey(user.getId()) &&
                usersData.get(user.getId()).getVersion() > user.getVersion()) {
            throw new VersionMismatchException();
        }

        user.incrementVersion();
        usersData.put(user.getId(), user);
    }

    @Override
    public Collection<User> getUsers() {
        return usersData.values();
    }
}
