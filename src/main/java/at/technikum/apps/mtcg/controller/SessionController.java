package at.technikum.apps.mtcg.controller;


import at.technikum.apps.mtcg.entity.Token;
import at.technikum.apps.mtcg.repository.UserRepository;
import at.technikum.apps.mtcg.service.SessionService;
import at.technikum.server.http.HttpContentType;
import at.technikum.server.http.HttpStatus;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;
import at.technikum.apps.mtcg.repository.UserRepository;
import at.technikum.apps.mtcg.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.SQLException;

public class SessionController extends Controller {

    private final SessionService sessionService;

    public SessionController(){ this.sessionService = new SessionService();}
    @Override
    public boolean supports(String route) {
        return route.equals("/sessions");
    }

    @Override
    public Response handle(Request request) {


        if (request.getRoute().equals("/sessions")) {
            switch (request.getMethod()) {
                case "POST": return login(request);
                default:
                    return status(HttpStatus.METHOD_NOT_ALLOWED);
            }

        }

        return status(HttpStatus.BAD_REQUEST);
    }

    private Response login(Request request){

        ObjectMapper objectMapper = new ObjectMapper();

        User user = null;

        try {
            user = objectMapper.readValue(request.getBody(), User.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Token token = null;


        try {
            token = sessionService.login(user);
        } catch(RuntimeException | SQLException e) {
            return status(HttpStatus.LOGIN_INVALID);
        }


        String tokenJson = null;

        try {
            tokenJson = objectMapper.writeValueAsString(token.getToken());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Response response = new Response();
        response.setStatus(HttpStatus.LOGIN_SUCCESSFUL);
        response.setContentType(HttpContentType.APPLICATION_JSON);
        response.setBody(tokenJson);

        return response;

    }

}
