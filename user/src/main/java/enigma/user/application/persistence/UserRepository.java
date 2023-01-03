package enigma.user.application.persistence;

import enigma.user.application.exception.EntityNotFoundException;
import enigma.user.application.exception.VersionMismatchException;
import enigma.user.domain.entity.User;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(String id);
    Optional<User> findByUsername(String username);
    void create(User user);
    void update(User user) throws EntityNotFoundException, VersionMismatchException;
    Collection<User> getUsers();
}
