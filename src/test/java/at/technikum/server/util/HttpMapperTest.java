package at.technikum.server.util;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class HttpMapperTest {

    @Test
    void getUsername() throws IOException {

        String httpRequest = "GET /example HTTP/1.1\r\n" +
                "Authorization: Bearer test-username\r\n" +
                "Host: localhost\r\n\r\n";
        String expectedUsername = "test";
        String actualUsername = HttpMapper.getUsername(httpRequest);
        assertEquals(expectedUsername, actualUsername);
    }

    @Test
    void getToken() throws IOException {

        String httpRequest = "GET /example HTTP/1.1\r\n" +
                "Authorization: Bearer test-mtcgToken\r\n" +
                "Host: localhost\r\n\r\n";
        String expectedToken = "test-mtcgToken";

        String actualToken = HttpMapper.getTokenNotAdmin(httpRequest);
        assertEquals(expectedToken, actualToken);
    }

    @Test
    void getWrongToken() throws IOException {

        String httpRequest = "GET /example HTTP/1.1\r\n" +
                "Authorization: Bearer test-mtcg\r\n" +
                "Host: localhost\r\n\r\n";
        String expectedToken = "test-mtcgToken";

        String result = HttpMapper.getTokenNotAdmin(httpRequest);
        assertEquals("INVALID", result);
    }



}