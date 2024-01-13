package at.technikum.apps.mtcg.service;

import at.technikum.apps.mtcg.entity.cards.Card;
import at.technikum.apps.mtcg.repository.DeckRepository;
import at.technikum.server.http.HttpStatus;
import at.technikum.server.http.Response;


import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class DeckService {

    private final DeckRepository deckRepository;
    public DeckService() {
        this.deckRepository = new DeckRepository();
    }
    public List<Card> showDeck(String username) {
        List<Card> cards = deckRepository.showDeck(username);

        if (cards.isEmpty()) {
            return Collections.emptyList(); // Optional: Throw an exception if no cards are found
        }

        return cards;
    }

    public void configureDeck(String username, String[] cards) throws SQLException {

        deckRepository.configureDeck(username, cards);

    }
}
