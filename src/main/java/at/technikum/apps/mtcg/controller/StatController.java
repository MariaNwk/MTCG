package at.technikum.apps.mtcg.controller;

import at.technikum.apps.mtcg.entity.UserStat;
import at.technikum.apps.mtcg.service.StatService;
import at.technikum.server.http.HttpContentType;
import at.technikum.server.http.HttpStatus;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

public class StatController extends Controller{

    private final StatService statService;

    public StatController(StatService statService){
        this.statService = statService;
    }
    ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public boolean supports(String route) {
        return route.equals("/stats");
    }

    @Override
    public Response handle(Request request) {



        if (request.getRoute().equals("/stats")) {
            switch (request.getMethod()) {
                case "GET": return retrieveStats(request);
                default:
                    return status(HttpStatus.METHOD_NOT_ALLOWED);
            }

        }

        return status(HttpStatus.BAD_REQUEST);
    }

    public Response retrieveStats(Request request) {



        if (request.getToken().equals("INVALID")|| !request.getToken().contains("mtcgToken"))
        {
            return status(HttpStatus.UNAUTHORIZED);
        }

        String username = request.getUsername();

        Optional<UserStat> userStats;

        userStats = statService.retrieveStats(username);


        if (userStats.isEmpty())
        {
            Response response = new Response();
            response.setStatus(HttpStatus.NOT_FOUND);
            response.setContentType(HttpContentType.APPLICATION_JSON);
            response.setBody("Stats not found");

            return response;
        }

        String statsJson;

        try{
            statsJson = objectMapper.writeValueAsString(userStats.get());
        } catch(JsonProcessingException e){
            Response response = new Response();
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setContentType(HttpContentType.APPLICATION_JSON);
            response.setBody("Internal Server error");
            return response;
        }

        Response response = new Response();
        response.setStatus(HttpStatus.OK);
        response.setBody(statsJson);
        return response;

    }
}
