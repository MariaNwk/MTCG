package at.technikum.apps.mtcg.entity.cards;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardDefinitionTest {

    @Test
    void createMonsterDefinition() {
        CardDefinition definition = CardDefinition.createDefinition("WaterGoblin");
        assertTrue(definition.isMonster());
        assertEquals("water", definition.getElementType());
    }

    @Test
    void createSpellDefinition() {
        CardDefinition definition = CardDefinition.createDefinition("FireSpell");
        assertFalse(definition.isMonster());
        assertEquals("fire", definition.getElementType());
    }

    @Test
    void createRegularElfDefinition() {
        CardDefinition definition = CardDefinition.createDefinition("RegularElf");
        assertTrue(definition.isMonster());
        assertEquals("regular", definition.getElementType());
    }


    @Test
    void createInvalidCardTypee() {
        String invalidCardType = "invalidcardtype";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> CardDefinition.createDefinition(invalidCardType));

        assertEquals("Invalid card type: " + invalidCardType, exception.getMessage());
    }

}