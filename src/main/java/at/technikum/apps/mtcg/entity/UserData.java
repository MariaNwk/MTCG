package at.technikum.apps.mtcg.entity;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserData {



    @JsonProperty("Username")
    private String username;

    @JsonProperty("Name")
    private String name;
    @JsonProperty("Bio")
    private String bio;

    @JsonProperty("Image")
    private String image;

    public UserData(String Username, String Name, String Bio, String Image) {
        this.username = Username;
        this.name = Name;
        this.bio = Bio;
        this.image = Image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String Username) {
        this.username = Username;
    }

    public String getName() {
        return name;
    }

    public void setName(String Name) {
        this.name = Name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String Bio) {
        this.bio = Bio;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String Image) {
        this.image = Image;
    }
}
