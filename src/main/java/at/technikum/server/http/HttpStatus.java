package at.technikum.server.http;

// THOUGHT: Add new relevant status (https://developer.mozilla.org/en-US/docs/Web/HTTP/Status)
public enum HttpStatus {
    OK(200, "Data successfully retrieved"),
    LOGIN_SUCCESSFUL(200, "User login successful"),
    UPDATE_SUCCESSFUL(200, "User successfully updated."),
    USERDATA_RETRIEVED(200, "Data successfully retrieved"),
    PACKAGE_BOUGHT(200, "A package has been successfully bought"),
    CREATED(201, "User successfully created"),
    PACKAGE_CREATED(201, "Package and cards successfully created"),
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Access token is missing or invalid"),
    LOGIN_INVALID(401, "Invalid username/password provided"),
    NOT_ADMIN(403, "Provided user is not admin"),
    MONEY_MISSING(403, "Not enough money for buying a card package"),
    PACKAGE_NOT_AVAILABLE(404, "No card package available for buying"),
    USER_NOT_FOUND(404, "User not found."),
    NOT_FOUND(404, "Not found."),
    METHOD_NOT_ALLOWED(405, "Method not allowed"),
    ALREADY_EXISTING(409, "User with same username already registered"),
    PACKAGE_ALREADY_EXISTING(409, "At least one card in the packages already exists"),
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
