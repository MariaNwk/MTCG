package at.technikum.server.http;

// THOUGHT: Add new relevant status (https://developer.mozilla.org/en-US/docs/Web/HTTP/Status)
public enum HttpStatus {
    OK(200, "Data successfully retrieved"),
    LOGIN_SUCCESSFUL(200, "User login successful"),
    UPDATE_SUCCESSFUL(200, "User successfully updated."),
    USERDATA_RETRIEVED(200, "Data successfully retrieved"),
    CREATED(201, "User successfully created"),
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Access token is missing or invalid"),
    LOGIN_INVALID(401, "Invalid username/password provided"),
    USER_NOT_FOUND(404, "User not found."),
    NOT_FOUND(404, "Not found."),
    METHOD_NOT_ALLOWED(405, "Method not allowed"),
    ALREADY_EXISTING(409, "User with same username already registered"),
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
