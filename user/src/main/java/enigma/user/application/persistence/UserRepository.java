package enigma.user.application.persistence;

import enigma.user.application.exception.VersionMismatchException;
import enigma.user.domain.entity.User;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(String id);
    Optional<User> findByUsername(String username);
    Collection<User> getUsers();
    void save(User user) throws VersionMismatchException;
}
