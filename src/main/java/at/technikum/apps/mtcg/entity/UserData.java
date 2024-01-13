package at.technikum.apps.mtcg.entity;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserData {


    @JsonProperty("Name")
    private String name;
    @JsonProperty("Bio")
    private String bio;
    @JsonProperty("Image")
    private String image;
    public UserData(){
    }

    public UserData(String Name, String Bio, String Image) {
        this.name = Name;
        this.bio = Bio;
        this.image = Image;
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
