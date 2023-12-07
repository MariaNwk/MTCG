package at.technikum.apps.mtcg.repository;

import at.technikum.apps.mtcg.entity.User;


import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> findAll();

    Optional<User> find(String username);

    User save(User user);

    User delete(User user);
}