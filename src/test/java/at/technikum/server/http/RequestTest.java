package at.technikum.server.http;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequestTest {

    @Test
    void isAdmin() {
        Request request = new Request();
        request.setToken("admin-mtcgToken");
        assertTrue(request.isAdmin());
    }

    @Test
    void isNotAdmin() {
        Request request = new Request();
        request.setToken("test-mtcgToken");
        assertFalse(request.isAdmin());
    }


}