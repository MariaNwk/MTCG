package at.technikum.apps.mtcg.controller;

import at.technikum.apps.mtcg.entity.User;
import at.technikum.apps.mtcg.entity.UserData;
import at.technikum.apps.mtcg.service.UserService;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserControllerTest {


    @Test
    void getUserDataByUsername()
    {
        UserService userServiceMock=mock(UserService.class);
        Request requestMock = mock(Request.class);
        UserController userController = new UserController(userServiceMock);

        requestMock.setRoute("/users/tester");
        requestMock.setToken("tester-mtcgToken");
        String tester = "tester";
        String usernamePath = "tester";

        User user = new User("tester", "pw");
        UserData userdata = new UserData(tester, "bio", "image");


        when(requestMock.getToken()).thenReturn("tester-mtcgToken");
        when(requestMock.getUsername()).thenReturn("tester");
        when(userServiceMock.getUserData("tester")).thenReturn(Optional.of(userdata));

        //when
        Response response = userController.getUserData(usernamePath, requestMock);

        //then
        assertEquals("{\"Name\":\"tester\",\"Bio\":\"bio\",\"Image\":\"image\"}", response.getBody());
    }

    @Test
    void getUserDataByUsernameInvalidToken()
    {
        UserService userServiceMock=mock(UserService.class);
        Request requestMock = mock(Request.class);
        UserController userController = new UserController(userServiceMock);


        requestMock.setRoute("/users/tester");
        requestMock.setToken("tester-mtcgToken");
        String tester = "tester";
        String usernamePath = "tester";

        requestMock.setToken("invalid-token");
        when(requestMock.getToken()).thenReturn("invalid-token");

        Response response = userController.getUserData(usernamePath, requestMock);

        //then
        assertEquals("{ \"error\": \"Access token is missing or invalid\"}", response.getBody());
    }
}