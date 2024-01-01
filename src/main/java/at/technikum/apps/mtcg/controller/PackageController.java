package at.technikum.apps.mtcg.controller;

import at.technikum.apps.mtcg.entity.Package;
import at.technikum.apps.mtcg.entity.User;
import at.technikum.apps.mtcg.entity.cards.Card;
import at.technikum.apps.mtcg.repository.CardRepository;
import at.technikum.apps.mtcg.repository.PackageRepository;
import at.technikum.apps.mtcg.repository.UserRepository;
import at.technikum.apps.mtcg.service.PackageService;
import at.technikum.server.http.HttpContentType;
import at.technikum.server.http.HttpStatus;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PackageController extends Controller{

    private final PackageService packageService;

    public PackageController(){this.packageService = new PackageService();}

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public boolean supports(String route) {
        return route.equals("/packages");
    }

    @Override
    public Response handle(Request request) {


        if (request.getRoute().equals("/packages")) {
            switch (request.getMethod()) {
                case "POST": return createPackage(request);
                default:
                    return status(HttpStatus.METHOD_NOT_ALLOWED);
            }

        }
        return status(HttpStatus.BAD_REQUEST);
    }

    public Response createPackage(Request request) {

        ObjectMapper objectMapper = new ObjectMapper();
        String token = request.getToken();

        if (request.getToken().equals("INVALID"))
        {
            return status(HttpStatus.UNAUTHORIZED);
        }

        if(!token.equals("admin-mtcgToken")){
            return status(HttpStatus.NOT_ADMIN);
        }


        Card[] cards;

        try {
            cards = objectMapper.readValue(request.getBody(), Card[].class);
            if (cards.length != 5)
            {
                return status(HttpStatus.BAD_REQUEST);
            }

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Before packageService.create(cards)");

        try {
                packageService.create(cards);
            } catch(RuntimeException e) {
                return status(HttpStatus.ALREADY_EXISTING);
            }

        System.out.println("After packageService.create(cards)");


        String packageJson = null;

        try {
            packageJson = objectMapper.writeValueAsString(cards);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Response response = new Response();
        response.setStatus(HttpStatus.PACKAGE_CREATED);
        response.setContentType(HttpContentType.APPLICATION_JSON);
        response.setBody(packageJson);

        return response;

    }

}
