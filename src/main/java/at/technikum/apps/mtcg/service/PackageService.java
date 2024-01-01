package at.technikum.apps.mtcg.service;

import at.technikum.apps.mtcg.entity.Package;
import at.technikum.apps.mtcg.entity.cards.Card;
import at.technikum.apps.mtcg.entity.cards.CardExtended;
import at.technikum.apps.mtcg.entity.cards.CardType;
import at.technikum.apps.mtcg.repository.CardRepository;
import at.technikum.apps.mtcg.repository.PackageRepository;
import at.technikum.apps.mtcg.repository.UserRepository;

public class PackageService {

    private final PackageRepository packageRepository;
    private final CardRepository cardRepository;


    public PackageService() {
        this.packageRepository = new PackageRepository();
        this.cardRepository = new CardRepository();

    }

    public void create(Card[] cards) {

        for (Card card : cards) {
            CardType cardType = CardType.createType(card.getName());
            CardExtended cardExtended = new CardExtended(card.getId(), card.getName(), card.getDamage(),cardType.isMonster(), cardType.getElementType().toString()) ;
            cardRepository.createCard(cardExtended);
        }
        packageRepository.save(cards);
    }
}
