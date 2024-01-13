package at.technikum.apps.mtcg.service;

import at.technikum.apps.mtcg.entity.cards.Card;
import at.technikum.apps.mtcg.entity.cards.CardDefinition;
import at.technikum.apps.mtcg.entity.cards.CardExtended;
import at.technikum.apps.mtcg.repository.CardRepository;
import at.technikum.apps.mtcg.repository.PackageRepository;
import at.technikum.apps.mtcg.repository.UserRepository;

import java.sql.SQLException;
import java.util.List;

public class PackageService {

    private final PackageRepository packageRepository;
    private final CardRepository cardRepository;
    private final UserRepository userRepository;


    public PackageService() {
        this.packageRepository = new PackageRepository();
        this.cardRepository = new CardRepository();
        this.userRepository = new UserRepository();
    }

    public void create(Card[] cards) {


        for (Card card : cards) {
            CardDefinition cardDefinition = CardDefinition.createDefinition(card.getName());
            CardExtended cardExtended = new CardExtended(
                    card.getId(), card.getName(), card.getDamage(),
                    cardDefinition.isMonster(), cardDefinition.getElementType());
            cardRepository.createCard(cardExtended);
        }
        packageRepository.save(cards);
    }


    public int getCoins(String username){
        int coins = userRepository.getCoins(username);
        return coins;
    }

    public Card[] buy(String username, int coins){

        List<Integer> ids;
        ids = packageRepository.getAvailablePackages();

        if(ids.isEmpty()){
            return null;
        }

        Card[] cards;
        try {
            cards = packageRepository.buyPackage(username, coins, ids.get(0)).toArray(new Card[0]);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return cards;
    }
}



