package at.technikum.apps.mtcg.entity;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Token {


    @JsonProperty("Token")
    public String token;

    public Token(String Token) {
        this.token = Token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}
