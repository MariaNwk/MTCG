package at.technikum.apps.mtcg.controller;

import at.technikum.apps.mtcg.entity.User;
import at.technikum.apps.mtcg.entity.UserStat;
import at.technikum.apps.mtcg.service.StatService;
import at.technikum.server.http.Response;
import at.technikum.server.http.Request;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StatControllerTest {
    @Test
    void getUserStats() {
        // Given
        StatService statServiceMock = mock(StatService.class);
        StatController statController = new StatController(statServiceMock);
        Request requestMock = mock(Request.class);

        requestMock.setToken("tester-mtcgToken");
        requestMock.setUsername("tester");

        UserStat userStat = new UserStat("tester", 30, 7, 3);

        when(requestMock.getToken()).thenReturn("tester-mtcgToken");
        when(requestMock.getUsername()).thenReturn("tester");
        when(statServiceMock.retrieveStats("tester")).thenReturn(Optional.of(userStat));

        // When
        Response response = statController.retrieveStats(requestMock);

        // Then
        assertEquals("{\"Name\":\"tester\",\"Elo\":30,\"Wins\":7,\"Losses\":3}", response.getBody());
    }

}