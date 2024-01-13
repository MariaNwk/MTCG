package at.technikum.apps.mtcg.service;

import at.technikum.apps.mtcg.entity.UserStat;
import at.technikum.apps.mtcg.repository.ScoreboardRepository;

import java.util.List;

public class ScoreboardService {
    private final ScoreboardRepository scoreboardRepository;



    public ScoreboardService() {
        this.scoreboardRepository = new ScoreboardRepository();
    }

    public List<UserStat> retrieveScoreboard() {
        List<UserStat> scoreboard;
        return scoreboard = scoreboardRepository.getScoreboard();

    }
}
