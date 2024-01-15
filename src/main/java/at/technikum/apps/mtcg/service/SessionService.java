package at.technikum.apps.mtcg.service;

import at.technikum.apps.mtcg.entity.Token;
import at.technikum.apps.mtcg.entity.User;
import at.technikum.apps.mtcg.repository.SessionRepository;


import java.sql.SQLException;

public class SessionService {

    private final SessionRepository sessionRepository;

    public SessionService() {
        this.sessionRepository = new SessionRepository();
    }

    public Token login(User user) throws SQLException {

        User userFromDB = sessionRepository.getUser(user.getUsername());

        if(userFromDB == null || !userFromDB.getPassword().equals(user.getPassword())){
            throw new SQLException("Invalid username/password provided");
        }

        return sessionRepository.login(userFromDB.getUsername());
    }

}
