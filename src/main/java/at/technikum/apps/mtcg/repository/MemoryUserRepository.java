package at.technikum.apps.mtcg.repository;

import at.technikum.apps.mtcg.entity.User;


import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public class MemoryUserRepository implements UserRepository{

    private final List<User> users;

    public MemoryUserRepository(){this.users = new ArrayList<>(); }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public Optional<User> find(String username) {
        return Optional.empty();
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public User delete(User user) {
        return null;
    }
}
