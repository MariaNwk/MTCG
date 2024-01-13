package at.technikum.apps.mtcg.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserStat {

    //String Name, int Elo, int Wins, int Losses

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Elo")
    private int elo;
    @JsonProperty("Wins")
    private int wins;
    @JsonProperty("Losses")
    private int losses;

    public UserStat(){

    }

    public UserStat(String Name, int Elo, int Wins, int Losses){
        this.name = Name;
        this.elo = Elo;
        this.wins = Wins;
        this.losses = Losses;
    }

    public int getElo() {
        return elo;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
