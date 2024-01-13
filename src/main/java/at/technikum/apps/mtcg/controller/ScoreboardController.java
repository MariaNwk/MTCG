package at.technikum.apps.mtcg.controller;

import at.technikum.apps.mtcg.entity.UserStat;
import at.technikum.apps.mtcg.service.ScoreboardService;
import at.technikum.server.http.HttpContentType;
import at.technikum.server.http.HttpStatus;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Optional;

public class ScoreboardController extends Controller{

    private final ScoreboardService scoreboardService = new ScoreboardService();
    ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public boolean supports(String route) {
        return route.equals("/scoreboard");
    }

    @Override
    public Response handle(Request request) {



        if (request.getRoute().equals("/scoreboard")) {
            switch (request.getMethod()) {
                case "GET": return retrieveScoreboard(request);
                default:
                    return status(HttpStatus.METHOD_NOT_ALLOWED);
            }

        }

        return status(HttpStatus.BAD_REQUEST);
    }


    public Response retrieveScoreboard(Request request) {



        if (request.getTokenNotAdmin().equals("INVALID"))
        {
            return status(HttpStatus.UNAUTHORIZED);
        }

        String username = request.getUsername();

        List<UserStat> scoreboard;

        scoreboard = scoreboardService.retrieveScoreboard();


        if (scoreboard.isEmpty())
        {
            Response response = new Response();
            response.setStatus(HttpStatus.NOT_FOUND);
            response.setContentType(HttpContentType.APPLICATION_JSON);
            response.setBody("Scoreboard not found");

            return response;
        }

        String scoreboardJson;

        try{
            scoreboardJson = objectMapper.writeValueAsString(scoreboard);
        } catch(JsonProcessingException e){
            Response response = new Response();
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setContentType(HttpContentType.APPLICATION_JSON);
            response.setBody("Internal Server error");
            return response;
        }

        Response response = new Response();
        response.setStatus(HttpStatus.OK);
        response.setBody(scoreboardJson);
        return response;


    }


}
