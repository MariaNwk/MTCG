package at.technikum.apps.mtcg.repository;

import at.technikum.apps.mtcg.controller.ScoreboardController;
import at.technikum.apps.mtcg.entity.UserStat;
import at.technikum.apps.mtcg.service.ScoreboardService;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ScoreboardRepositoryTest {


    @Test
    void testGetScoreboard() {


        ScoreboardService scoreboardServiceMock = mock(ScoreboardService.class);
        Request requestMock = mock(Request.class);
        ScoreboardController scoreboardController = new ScoreboardController(scoreboardServiceMock);

        requestMock.setToken("tester-mtcgToken");

        List<UserStat> scoreboard = List.of(
                new UserStat("name1", 5, 5, 0),
                new UserStat("name2", 2, 2, 0)
        );

        when(requestMock.getToken()).thenReturn("tester-mtcgToken");
        when(scoreboardServiceMock.retrieveScoreboard()).thenReturn(scoreboard);

        Response response = scoreboardController.retrieveScoreboard(requestMock);
        //then
        assertEquals("[{\"Name\":\"name1\",\"Elo\":5,\"Wins\":5,\"Losses\":0},{\"Name\":\"name2\",\"Elo\":2,\"Wins\":2,\"Losses\":0}]",
                response.getBody());
    }

    @Test
    void testInvalidTokenScoreboard() {

        ScoreboardService scoreboardServiceMock = mock(ScoreboardService.class);
        Request requestMock = mock(Request.class);
        ScoreboardController scoreboardController = new ScoreboardController(scoreboardServiceMock);

        requestMock.setToken("invalid-token");
        when(requestMock.getToken()).thenReturn("invalid-token");

        Response response = scoreboardController.retrieveScoreboard(requestMock);

        //then
        assertEquals("{ \"error\": \"Access token is missing or invalid\"}", response.getBody());
    }



}