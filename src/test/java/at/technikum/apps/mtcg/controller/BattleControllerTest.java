package at.technikum.apps.mtcg.controller;

import at.technikum.apps.mtcg.entity.cards.CardExtended;
import at.technikum.apps.mtcg.service.BattleService;
import at.technikum.server.http.Request;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class BattleControllerTest {

    @Test
    void testMonsterFight()
    {
        //given
        BattleService battleServiceMock = mock(BattleService.class);
        BattleController battleController = new BattleController(battleServiceMock);



        CardExtended attacker = new CardExtended("one", "FireGoblin", 15, true, "fire");
        CardExtended defender = new CardExtended("two", "Dragon", 10, true, "regular");

        CardExtended attacker1 = new CardExtended("three", "Wizzard", 10, true, "regular");
        CardExtended defender1 = new CardExtended("four", "Ork", 10, true, "regular");


        float damageOne = attacker.getDamage();
        float damageTwo = attacker1.getDamage();
        //when
        float damageGoblin = battleController.categorizeFight(attacker, defender, damageOne);
        float damageWizzard = battleController.categorizeFight(attacker1, defender1, damageTwo);
        //then
        assertEquals(0, damageGoblin);
        assertEquals(5000, damageWizzard);
    }

    @Test
    void testMixedFight()
    {
        //given
        BattleService battleServiceMock = mock(BattleService.class);
        BattleController battleController = new BattleController(battleServiceMock);


        CardExtended attacker = new CardExtended("one", "WaterSpell", 15, false, "water");
        CardExtended defender = new CardExtended("two", "Kraken", 10, true, "regular");

        CardExtended attacker1 = new CardExtended("three", "WaterSpell", 10, false, "water");
        CardExtended defender1 = new CardExtended("four", "Knight", 10, true, "regular");


        float damageOne = attacker.getDamage();
        float damageTwo = attacker1.getDamage();
        //when
        float damageSpell = battleController.categorizeFight(attacker, defender, damageOne);
        float damageWaterSpell = battleController.categorizeFight(attacker1, defender1, damageTwo);
        //then
        assertEquals(0, damageSpell);
        assertEquals(5000, damageWaterSpell);
    }

    @Test
    void testSpellFight()
    {
        //given
        BattleService battleServiceMock = mock(BattleService.class);
        BattleController battleController = new BattleController(battleServiceMock);


        CardExtended attacker = new CardExtended("one", "WaterSpell", 15, false, "water");
        CardExtended defender = new CardExtended("two", "FireSpell", 10, false, "fire");

        CardExtended attacker1 = new CardExtended("three", "RegularSpell", 10, false, "regular");
        CardExtended defender1 = new CardExtended("four", "FireSpell", 10, false, "fire");


        float damageOne = attacker.getDamage();
        float damageTwo = attacker1.getDamage();
        //when
        float damageWater = battleController.categorizeFight(attacker, defender, damageOne);
        float damageWaterSpell = battleController.categorizeFight(attacker1, defender1, damageTwo);
        //then
        assertEquals(30, damageWater);
        assertEquals(5, damageWaterSpell);
    }



}