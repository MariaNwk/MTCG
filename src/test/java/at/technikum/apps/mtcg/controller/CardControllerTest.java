package at.technikum.apps.mtcg.controller;

import at.technikum.apps.mtcg.entity.User;
import at.technikum.apps.mtcg.entity.cards.Card;
import at.technikum.apps.mtcg.service.CardService;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CardControllerTest {

    @Test
    void getAllCards()
    {
        //given

        CardService cardServiceMock = mock(CardService.class);
        Request requestMock = mock(Request.class);
        CardController cardController = new CardController(cardServiceMock);

        requestMock.setToken("tester-mtcgToken");
        requestMock.setUsername("tester");



        List<Card> cards = List.of(
                new Card("id1", "card1", 10),
                new Card("id2", "card2", 20),
                new Card("id3", "card3", 30)
        );

        when(requestMock.getToken()).thenReturn("tester-mtcgToken");
        when(requestMock.getUsername()).thenReturn("tester");
        when(cardServiceMock.showCardsFromUser("tester")).thenReturn(cards);

        //when
        Response response = cardController.showCardsFromUser(requestMock);

        //then
        assertEquals("[{\"Id\":\"id1\",\"Name\":\"card1\",\"Damage\":10.0},{\"Id\":\"id2\",\"Name\":\"card2\",\"Damage\":20.0},{\"Id\":\"id3\",\"Name\":\"card3\",\"Damage\":30.0}]",
                response.getBody());
    }

    @Test
    void requestFineButUserNoCards()
    {
        //given

        CardService cardServiceMock = mock(CardService.class);
        Request requestMock = mock(Request.class);
        CardController cardController = new CardController(cardServiceMock);

        requestMock.setToken("tester-mtcgToken");

        User user = new User("tester", "pw");

        List<Card> cards = null;

        when(requestMock.getToken()).thenReturn("tester-mtcgToken");
        when(requestMock.getUsername()).thenReturn("tester");
        when(cardServiceMock.showCardsFromUser("tester")).thenReturn(cards);


        //when
        Response response = cardController.showCardsFromUser(requestMock);

        //then
        assertEquals("{ \"error\": \"The request was fine, but the user doesn't have any cards\"}",
                response.getBody());
    }

    @Test
    void testInvalidToken() {

        CardService cardServiceMock = mock(CardService.class);
        Request requestMock = mock(Request.class);
        CardController cardController = new CardController(cardServiceMock);

        requestMock.setToken("invalid-token");
        when(requestMock.getToken()).thenReturn("invalid-token");

        Response response = cardController.showCardsFromUser(requestMock);

        //then
        assertEquals("{ \"error\": \"Access token is missing or invalid\"}", response.getBody());
    }

}

