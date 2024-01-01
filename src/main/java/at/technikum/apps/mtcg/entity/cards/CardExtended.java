package at.technikum.apps.mtcg.entity.cards;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CardExtended {
    @JsonProperty("CardId")
    public String cardId;

    @JsonProperty("Card_name")
    public String card_name;

    @JsonProperty("Damage")
    public float damage;

    @JsonProperty("MonsterCard")
    public boolean monsterCard;

    @JsonProperty("Element")
    public String element;





    public CardExtended(String CardId, String Card_name, float Damage, boolean MonsterCard, String Element){
        this.cardId = CardId;
        this.card_name = Card_name;
        this.damage = Damage;
        this.monsterCard = MonsterCard;
        this.element = Element;
    }

    public String getCardId() {
        return cardId;
    }

    public float getDamage() {
        return damage;
    }

    public String getCard_name() {
        return card_name;
    }

    public boolean isMonsterCard() {
        return monsterCard;
    }

    public String getElement() {
        return element;
    }
}
