package at.technikum.apps.mtcg.controller;

import at.technikum.apps.mtcg.entity.User;
import at.technikum.apps.mtcg.entity.UserData;
import at.technikum.apps.mtcg.repository.UserRepository;
import at.technikum.apps.mtcg.service.UserService;
import at.technikum.server.http.HttpContentType;
import at.technikum.server.http.HttpStatus;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.util.List;
import java.util.Objects;
import java.util.Optional;


public class UserController extends Controller {

    private final UserService userService;


    public UserController() {
        this.userService = new UserService(new UserRepository());
    }

    @Override
    public boolean supports(String route) {
        return route.startsWith("/users");
    }


    @Override
    public Response handle(Request request) {


        //Register User
        if (request.getRoute().equals("/users")) {
            switch (request.getMethod()) {
                case "POST": return create(request);
                default:
                    return status(HttpStatus.METHOD_NOT_ALLOWED);
            }

        } else {

            //Registered User
            //get username e.g. from /username/{username}
            String[] routeParts = request.getRoute().split("/");

            if(routeParts.length == 3 && routeParts[1].equals("users")){

                String username = routeParts[2];

                switch (request.getMethod()) {
                    case "GET":
                        return getUserData(username, request);  //Userdata anfragen, findUserData()
                    case "PUT":
                        return update(username, request); //
                    default:
                        return status(HttpStatus.METHOD_NOT_ALLOWED);

                }
            }
            return status(HttpStatus.BAD_REQUEST);

        }


    }



    public Response create(Request request) {

        ObjectMapper objectMapper = new ObjectMapper();

        User user = null;

        try {
            user = objectMapper.readValue(request.getBody(), User.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        try {
            user = userService.save(user);
        } catch(RuntimeException e) {
            return status(HttpStatus.ALREADY_EXISTING);
        }

        String userJson = null;

        try {
            userJson = objectMapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Response response = new Response();
        response.setStatus(HttpStatus.CREATED);
        response.setContentType(HttpContentType.APPLICATION_JSON);
        response.setBody(userJson);

        return response;

    }



    //----------------------------------------------------------------
    //USERDATA

    public Response getUserData(String usernamePath, Request request) {


        ObjectMapper objectMapper = new ObjectMapper();


        if (request.getTokenNotAdmin().equals("INVALID"))
        {
            return status(HttpStatus.UNAUTHORIZED);
        }

        String username = request.getUsername();

        if
        (!Objects.equals(username, usernamePath)){
            return status(HttpStatus.UNAUTHORIZED);
        }

        Optional<UserData> userData = userService.getUserData(username);


        if(userData.isEmpty()){
            Response response = new Response();
            response.setStatus(HttpStatus.OK);
            response.setContentType(HttpContentType.APPLICATION_JSON);
            response.setBody("The request was fine, but the user doesn't have any user data");
            return response;
        }



        String userJson = null;

        try {
            userJson = objectMapper.writeValueAsString(userData.get());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }



        Response response = new Response();
        response.setStatus(HttpStatus.OK);
        response.setContentType(HttpContentType.APPLICATION_JSON);
        response.setBody(userJson);

        return response;


    }




    public Response update(String usernamePath, Request request) {

        ObjectMapper objectMapper = new ObjectMapper();

        if (request.getTokenNotAdmin().equals("INVALID"))
        {
            return status(HttpStatus.UNAUTHORIZED);
        }
        String username = request.getUsername();

        if(!Objects.equals(username, usernamePath)){
            return status(HttpStatus.UNAUTHORIZED);
        }

        UserData userData;

        try {
            userData = objectMapper.readValue(request.getBody(), UserData.class);
        } catch (JsonProcessingException e) {
            return status(HttpStatus.BAD_REQUEST);
        }


        try {
           userService.updateUser(username, userData);
        } catch(RuntimeException e) {
            return status(HttpStatus.BAD_REQUEST);
        }

        String userJson = null;

        try {
            userJson = objectMapper.writeValueAsString(userData);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Response response = new Response();
        response.setStatus(HttpStatus.UPDATE_SUCCESSFUL);
        response.setContentType(HttpContentType.APPLICATION_JSON);
        response.setBody(userJson);

        return response;

    }
}




