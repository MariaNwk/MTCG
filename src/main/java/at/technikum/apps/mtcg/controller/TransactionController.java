package at.technikum.apps.mtcg.controller;

import at.technikum.apps.mtcg.entity.cards.Card;
import at.technikum.apps.mtcg.service.PackageService;
import at.technikum.server.http.HttpContentType;
import at.technikum.server.http.HttpStatus;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TransactionController extends Controller{

    private final PackageService packageService;
    public TransactionController(){this.packageService = new PackageService();}
    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public boolean supports(String route) {
        return route.startsWith("/transactions");
    }

    @Override
    public Response handle(Request request) {

        if (request.getRoute().startsWith("/transactions")) {
            switch (request.getMethod()) {
                case "POST": return buyPackage(request);
                default:
                    return status(HttpStatus.METHOD_NOT_ALLOWED);
            }

        }
        return status(HttpStatus.BAD_REQUEST);
    }


    public Response buyPackage(Request request){

        ObjectMapper objectMapper = new ObjectMapper();

        if (request.getToken().equals("INVALID")|| !request.getToken().contains("mtcgToken"))
        {
            return status(HttpStatus.UNAUTHORIZED);
        }

        String username = request.getUsername();

        int coins;

        try {
            coins = packageService.getCoins(username);
        } catch(RuntimeException e) {
            return status(HttpStatus.BAD_REQUEST);
        }

        if (coins < 5){
            return status(HttpStatus.MONEY_MISSING);
        }

        Card[] cards;

        try {
            cards = packageService.buy(username, coins);
        } catch(RuntimeException e) {
            return status(HttpStatus.BAD_REQUEST);
        }

        if (cards == null){
            return status(HttpStatus.PACKAGE_NOT_AVAILABLE);
        }



        String packageJson = null;

        try {
            packageJson = objectMapper.writeValueAsString(cards);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Response response = new Response();
        response.setStatus(HttpStatus.PACKAGE_BOUGHT);
        response.setContentType(HttpContentType.APPLICATION_JSON);
        response.setBody(packageJson);

        return response;

    }



}
