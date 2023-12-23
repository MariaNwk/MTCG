package at.technikum.apps.mtcg.entity;

import com.fasterxml.jackson.annotation.JsonProperty;


public class User  {

    @JsonProperty("Username")
    public String username;

    @JsonProperty("Password")
    public String password;

    public User()
    {

    }

    public User(String Username, String Password) {
        this.username = Username;
        this.password = Password;
    }



    public String getUsername(){
        return username;
    }

    public void setUsername(String Username) {
        this.username = Username;
    }

    public String getPassword(){return password;}


}


