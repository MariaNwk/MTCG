package at.technikum.apps.mtcg.service;

import at.technikum.apps.mtcg.entity.Token;
import at.technikum.apps.mtcg.entity.User;
import at.technikum.apps.mtcg.repository.UserRepository;

import java.sql.SQLException;

public class SessionService {

    private final UserRepository userRepository;

    public SessionService(UserRepository userRepository) {
        this.userRepository = new UserRepository();
    }

    public Token login(User user) throws SQLException {
        return userRepository.authenticate(user);
    }

}
