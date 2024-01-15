package at.technikum.apps.mtcg.entity.cards;

import java.util.List;
import java.util.ArrayList;

public class CardDefinition {

    private final boolean isMonster;
    private final String elementType;

    public CardDefinition(boolean isMonster, String elementType) {
        this.isMonster = isMonster;
        this.elementType = elementType;
    }

    public boolean isMonster() {
        return isMonster;
    }

    public String getElementType() {
        return elementType;
    }

    public static CardDefinition createDefinition(String name) {
        switch (name.toLowerCase()) {
            case "watergoblin":
            case "firegoblin":
            case "regulargoblin":
                return new CardDefinition(true, name.substring(0, name.length() - 6).toLowerCase());
            case "watertroll":
            case "firetroll":
            case "regulartroll":
                return new CardDefinition(true, name.substring(0, name.length() - 5).toLowerCase());
            case "waterelf":
            case "fireelf":
            case "regularelf":
                return new CardDefinition(true, name.substring(0, name.length() - 3).toLowerCase());
            case "waterspell":
            case "firespell":
            case "regularspell":
                return new CardDefinition(false, name.substring(0, name.length() - 5).toLowerCase());
            case "knight":
            case "dragon":
            case "ork":
            case "kraken":
            case "wizzard":
            case "switch":
                return new CardDefinition(true, "regular");
            default:
                throw new IllegalArgumentException("Invalid card type: " + name);
        }
    }

}
