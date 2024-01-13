package at.technikum.server.http;

// THOUGHT: Add new relevant status (https://developer.mozilla.org/en-US/docs/Web/HTTP/Status)
public enum HttpStatus {
    OK(200, "Data successfully retrieved"),
    LOGIN_SUCCESSFUL(200, "User login successful"),
    UPDATE_SUCCESSFUL(200, "User successfully updated."),
    USERDATA_RETRIEVED(200, "Data successfully retrieved"),
    PACKAGE_BOUGHT(200, "A package has been successfully bought"),
    USER_CARDS_AVAILABLE(200, "The user has cards, the response contains these"),

    DECK_HAS_CARDS(200, "The deck has cards, the response contains these"),
    CREATED(201, "User successfully created"),
    PACKAGE_CREATED(201, "Package and cards successfully created"),
    OK_BUT_NO_CARDS(204, "The request was fine, but the user doesn't have any cards"),
    OK_BUT_NO_USERDATA(204, "The request was fine, but the user doesn't have any user data"),
    OK_BUT_NO_CARDS_IN_DECK(204, "The request was fine, but the deck doesn't have any cards"),

    BAD_REQUEST(400, "Bad Request"),
    NOT_ENOUGH_CARDS_FOR_DECK(400, "The provided deck did not include the required amount of cards"),
    UNAUTHORIZED(401, "Access token is missing or invalid"),
    LOGIN_INVALID(401, "Invalid username/password provided"),
    NOT_ADMIN(403, "Provided user is not admin"),
    MONEY_MISSING(403, "Not enough money for buying a card package"),

    DOES_NOT_BELONG_USER(403, "At least one of the provided cards does not belong to the user or is not available."),
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
