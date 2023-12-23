package at.technikum.server.http;

// THOUGHT: Add new relevant status (https://developer.mozilla.org/en-US/docs/Web/HTTP/Status)
public enum HttpStatus {
    OK(200, "Data successfully retrieved"),
    CREATED(201, "User successfully created"),
    ALREADY_EXISTING(409, "User with same username already registered"),
    NOT_FOUND(404, "Not Found"),
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Access token is missing or invalid"),
    FORBIDDEN(404, "User not found."),
    METHOD_NOT_ALLOWED(405, "Method not allowed"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error");


    private final int code;
    private final String message;

    HttpStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
