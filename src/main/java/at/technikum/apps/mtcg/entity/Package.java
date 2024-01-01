package at.technikum.apps.mtcg.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Package {

    @JsonProperty("PackageId")
    public String packageId;
    @JsonProperty("Card1")
    public String card1;
    @JsonProperty("Card2")
    public String card2;
    @JsonProperty("Card3")
    public String card3;
    @JsonProperty("Card4")
    public String card4;
    @JsonProperty("Card5")
    public String card5;




    public Package(String PackageId, String Card1, String Card2,String Card3,String Card4,String Card5 ){
        this.packageId = PackageId;
        this.card1 = Card1;
        this.card2 = Card2;
        this.card3 = Card3;
        this.card4 = Card4;
        this.card5 = Card5;

    }
}
