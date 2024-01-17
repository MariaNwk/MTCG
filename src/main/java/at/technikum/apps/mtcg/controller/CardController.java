package at.technikum.apps.mtcg.controller;

import at.technikum.apps.mtcg.entity.cards.Card;
import at.technikum.apps.mtcg.service.CardService;
import at.technikum.apps.mtcg.service.PackageService;
import at.technikum.server.http.HttpContentType;
import at.technikum.server.http.HttpStatus;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class CardController extends Controller{

    private final CardService cardService;
    public CardController(CardService cardService){this.cardService = cardService;}
    private final ObjectMapper objectMapper = new ObjectMapper();



    @Override
    public boolean supports(String route) {
        return route.equals("/cards");
    }

    @Override
    public Response handle(Request request) {

        if (request.getRoute().equals("/cards")) {
            switch (request.getMethod()) {
                case "GET": return showCardsFromUser(request);
                default:
                    return status(HttpStatus.METHOD_NOT_ALLOWED);
            }

        }
        return status(HttpStatus.BAD_REQUEST);
    }

    public Response showCardsFromUser(Request request) {

        ObjectMapper objectMapper = new ObjectMapper();

        if (request.getToken().equals("INVALID")|| !request.getToken().contains("mtcgToken"))
        {
            return status(HttpStatus.UNAUTHORIZED);
        }

        String username = request.getUsername();

        List<Card> cards;

        try {
            cards = cardService.showCardsFromUser(username);
        } catch(RuntimeException e) {
            return status(HttpStatus.BAD_REQUEST);
        }

        if(cards.isEmpty()){
            return status(HttpStatus.OK_BUT_NO_CARDS);
        }


        String cardsJson = null;
        try {
            cardsJson = objectMapper.writeValueAsString(cards);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Response response = new Response();
        response.setStatus(HttpStatus.USER_CARDS_AVAILABLE);
        response.setContentType(HttpContentType.APPLICATION_JSON);
        response.setBody(cardsJson);

        return response;

    }


}
