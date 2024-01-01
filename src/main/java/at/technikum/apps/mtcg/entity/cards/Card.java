package at.technikum.apps.mtcg.entity.cards;

import at.technikum.apps.mtcg.data.Database;
import com.fasterxml.jackson.annotation.JsonProperty;


public class Card {
    @JsonProperty("Id")
    public String id;

    @JsonProperty("Name")
    public String name;

    @JsonProperty("Damage")
    public float damage;

    // Leerer Standardkonstruktor
    public Card() {
    }

    public Card(String Id, String Name, float Damage) {
        this.id = Id;
        this.name = Name;
        this.damage = Damage;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getDamage() {
        return damage;
    }
}
