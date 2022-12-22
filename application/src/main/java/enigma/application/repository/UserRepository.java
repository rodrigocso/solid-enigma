package enigma.application.repository;

import java.util.Optional;

import enigma.application.exception.EntityNotFoundException;
import enigma.application.exception.VersionMismatchException;
import enigma.domain.entity.User;

public interface UserRepository {
    Optional<User> findById(String id);
    Optional<User> findByUsername(String username);
    void create(User user);
    void update(User user) throws EntityNotFoundException, VersionMismatchException;
}
