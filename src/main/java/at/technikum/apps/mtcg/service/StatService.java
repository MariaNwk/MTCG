package at.technikum.apps.mtcg.service;

import at.technikum.apps.mtcg.entity.UserStat;
import at.technikum.apps.mtcg.repository.StatRepository;
import at.technikum.server.http.HttpContentType;
import at.technikum.server.http.HttpStatus;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

public class StatService {

    private final StatRepository statRepository;


    public StatService() {
        this.statRepository = new StatRepository();
    }

    public Optional<UserStat> retrieveStats(String username) {

        Optional<UserStat> userStats;
        return userStats = statRepository.retrieveStats(username);

    }
}
