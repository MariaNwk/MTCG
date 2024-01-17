package at.technikum.apps.mtcg.controller;

import at.technikum.apps.mtcg.entity.User;
import at.technikum.apps.mtcg.entity.cards.Card;
import at.technikum.apps.mtcg.repository.SessionRepository;
import at.technikum.apps.mtcg.repository.UserRepository;
import at.technikum.apps.mtcg.service.DeckService;
import at.technikum.apps.mtcg.service.UserService;
import at.technikum.server.http.HttpContentType;
import at.technikum.server.http.HttpStatus;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.SQLException;
import java.util.List;

public class DeckController extends Controller{

    private final DeckService deckService;

    private final SessionRepository sessionRepository;


    public DeckController() {this.deckService = new DeckService();
    this.sessionRepository = new SessionRepository();}

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean supports(String route) {
        return route.startsWith("/deck");
    }

    @Override
    public Response handle(Request request) {


        if (request.getRoute().startsWith("/deck")) {
            switch (request.getMethod()) {
                case "GET": return showDeck(request);
                case "PUT": return configureDeck(request);
                default:
                    return status(HttpStatus.METHOD_NOT_ALLOWED);
            }

        }

        return status(HttpStatus.BAD_REQUEST);

    }



    public Response configureDeck(Request request) {

        if (request.getToken().equals("INVALID")|| !request.getToken().contains("mtcgToken"))
        {
            return status(HttpStatus.UNAUTHORIZED);
        }

        String username = request.getUsername();


        String[] cards;

        try
        {
            cards = objectMapper.readValue(request.getBody(), String[].class);
        }
        catch (JsonProcessingException e)
        {
            return status(HttpStatus.BAD_REQUEST);
        }

        if(cards.length != 4){

            return status(HttpStatus.NOT_ENOUGH_CARDS_FOR_DECK);

        }

        try {
            deckService.configureDeck(username, cards);
        } catch(RuntimeException e) {
            return status(HttpStatus.BAD_REQUEST);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Response response = new Response();
        response.setStatus(HttpStatus.OK);
        response.setContentType(HttpContentType.APPLICATION_JSON);
        response.setBody("Deck created");
        return response;
    }

    public Response showDeck(Request request) {

        if (request.getTokenNotAdmin().equals("INVALID") || !request.getToken().contains("mtcgToken"))
        {
            return status(HttpStatus.UNAUTHORIZED);
        }

        String username = request.getUsername();

        List<Card> cards;


        try {
            cards = deckService.showDeck(username);

            if(cards.isEmpty()){

                Response response = new Response();
                response.setStatus(HttpStatus.OK);
                response.setContentType(HttpContentType.APPLICATION_JSON);
                response.setBody("The request was fine, but the deck doesn't have any cards");
                return response;

            }

            if(request.getRoute().endsWith("format=plain")){
                StringBuilder plainFormat = new StringBuilder();
                for (Card card : cards) {
                    plainFormat.append(card.getName()).append("\n");
                }

                Response response = new Response();
                response.setStatus(HttpStatus.OK);
                response.setContentType(HttpContentType.TEXT_PLAIN);  // Set content type to plain text
                response.setBody(plainFormat.toString());
                return response;
            }


        ObjectMapper objectMapper = new ObjectMapper();
        String cardsJson;

        try {
            cardsJson = objectMapper.writeValueAsString(cards);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Response response = new Response();
        response.setStatus(HttpStatus.OK);
        response.setContentType(HttpContentType.APPLICATION_JSON);
        response.setBody(cardsJson);

        return response;

        } catch (RuntimeException e) {
            return status(HttpStatus.BAD_REQUEST);
        }

    }


}
