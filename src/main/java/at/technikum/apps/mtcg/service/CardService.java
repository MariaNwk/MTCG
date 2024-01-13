package at.technikum.apps.mtcg.service;

import at.technikum.apps.mtcg.entity.cards.Card;
import at.technikum.apps.mtcg.repository.CardRepository;
import at.technikum.apps.mtcg.repository.PackageRepository;
import at.technikum.apps.mtcg.repository.UserRepository;

import java.util.List;

public class CardService {

    private final CardRepository cardRepository;

    public CardService() {
        this.cardRepository = new CardRepository();
    }
    public List<Card> showCardsFromUser(String username) {
        return cardRepository.showCardsFromUser(username);
    }


}
