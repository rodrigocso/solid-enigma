package enigma.application.repository;

import java.util.Optional;

import enigma.domain.entity.User;

public interface UserRepository {
    Optional<User> findById(String id);
    void create(User user);
}
