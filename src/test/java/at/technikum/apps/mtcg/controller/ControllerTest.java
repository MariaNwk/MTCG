package at.technikum.apps.mtcg.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    @Test
    void supports() {

        Controller sessionController = new SessionController();
        Controller userController = new UserController();
        Controller transactionController = new TransactionController();
        Controller packageController = new PackageController();
        Controller cardController = new CardController();
        Controller deckController = new DeckController();
        Controller statController = new StatController();
        Controller scoreboardController = new ScoreboardController();
        Controller battleController = new BattleController();

        assertTrue(sessionController.supports("/sessions"));
        assertTrue(userController.supports("/users"));
        assertTrue(transactionController.supports("/transactions"));
        assertTrue(packageController.supports("/packages"));
        assertTrue(cardController.supports("/cards"));
        assertTrue(deckController.supports("/deck"));
        assertTrue(statController.supports("/stats"));
        assertTrue(scoreboardController.supports("/scoreboard"));
        assertTrue(battleController.supports("/battles"));

    }

}