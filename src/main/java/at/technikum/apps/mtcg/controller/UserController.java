package at.technikum.apps.mtcg.controller;

import at.technikum.apps.mtcg.entity.User;
import at.technikum.apps.mtcg.service.UserService;
import at.technikum.apps.task.entity.Task;
import at.technikum.apps.task.service.TaskService;
import at.technikum.server.http.HttpContentType;
import at.technikum.server.http.HttpStatus;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;


public class UserController extends Controller {

/*
    @Override
    public Response handle(Request request) {
        Response response = new Response();
        response.setStatus(HttpStatus.OK);
        response.setContentType(HttpContentType.TEXT_PLAIN);
        response.setBody("user controller");

        return response;
    }
*/
    private final UserService userService;

    public UserController() {
        this.userService = new UserService();
    }

    @Override
    public boolean supports(String route) {
        return route.startsWith("/users");
    }
    //Diese Methode prüft, ob der übergebene Routenpfad (route) gleich "/users" ist.
    //Wenn die Methode true zurückgibt, bedeutet dies, dass diese Controller-Klasse für die
    // Verarbeitung des angegebenen Routenpfads zuständig ist.

    @Override
    public Response handle(Request request) {


        // get username e.g. from /username/{username}


        String[] routeParts = request.getRoute().split("/");
        String username = routeParts[2];

        if (request.getRoute().equals("/users")) {
            switch (request.getMethod()) {
                case "GET": return readAll(request);
                case "POST": return create(request);
                case "DELETE":return deleteUsername(username,request);
            }

            // THOUGHT: better 405
            return status(HttpStatus.BAD_REQUEST);
        }


        // THOUGHT: better 405
        return status(HttpStatus.BAD_REQUEST);
    }


    public Response create(Request request) {

        ObjectMapper objectMapper = new ObjectMapper();
        User user = null;
        try {
            user = objectMapper.readValue(request.getBody(), User.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // user = toObject(request.getBody(), User.class);

        user = userService.save(user);

        String userJson = null;
        try {
            userJson = objectMapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Response response = new Response();
        // THOUGHT: better status 201 Created
        response.setStatus(HttpStatus.OK);
        response.setContentType(HttpContentType.APPLICATION_JSON);
        //response.setBody(userJson);

        return response;

        // return json(user);
    }

    public Response readAll(Request request) {
        List<User> users = userService.findAll();

        ObjectMapper objectMapper = new ObjectMapper();
        String usersJson = null;
        try {
            usersJson = objectMapper.writeValueAsString(users);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


        // Object to JSON coming soon

        Response response = new Response();
        // THOUGHT: better status 201 Created
        response.setStatus(HttpStatus.OK);
        response.setContentType(HttpContentType.APPLICATION_JSON);
        response.setBody(usersJson);

        return response;
    }

    public Response read(int id, Request request) {
        return null;
    }

    public Response update(int id, Request request) {
        return null;
    }

    public Response deleteUsername(String username, Request request) {
        return null;
    }

}
