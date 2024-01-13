package at.technikum.apps.mtcg.service;

import at.technikum.apps.mtcg.entity.cards.CardExtended;
import at.technikum.apps.mtcg.repository.BattleRepository;
import at.technikum.apps.mtcg.repository.CardRepository;
import at.technikum.apps.mtcg.repository.DeckRepository;
import at.technikum.apps.mtcg.repository.ScoreboardRepository;
import at.technikum.server.http.Request;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BattleService {
    private final BattleRepository battleRepository;
    public BattleService(){this.battleRepository = new BattleRepository();
    }


    public List<CardExtended> getDeck(String playerName) {
        return battleRepository.getDeck(playerName);
    }


    public void setStats(String winner, String loser){
        battleRepository.addWin(winner);
        battleRepository.takeLoss(loser);
        battleRepository.receiveElo(winner);
        battleRepository.giveElo(loser);
    }



}
