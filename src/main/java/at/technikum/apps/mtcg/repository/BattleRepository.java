package at.technikum.apps.mtcg.repository;

import at.technikum.apps.mtcg.entity.cards.CardExtended;
import at.technikum.apps.task.data.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BattleRepository {

    private final String GET_DETAIL_DECK = "SELECT cardID, card_name, damage, monsterCard, element FROM deck d JOIN card c ON d.card1 = c.cardID OR d.card2 = c.cardID OR d.card3 = c.cardID OR d.card4 = c.cardID WHERE d.deckID = ?";
    private final String RECEIVE_ELO = "UPDATE stats SET elo = elo + 3 WHERE name = ?";
    private final String GIVE_ELO = "UPDATE stats SET elo = elo - 5 WHERE name = ?";
    private final String ADD_WIN = "UPDATE stats SET wins = wins + 1 WHERE name = ?";
    private final String TAKE_LOSS = "UPDATE stats SET losses = losses + 1 WHERE name = ?";


    private final Database database = new Database();



    public List<CardExtended> getDeck(String username) {

        try
        {
            List<CardExtended> result = new ArrayList<>();
            Connection con = Database.getConnection();
            PreparedStatement pstmt = con.prepareStatement(GET_DETAIL_DECK);
            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();

            con.close();

            while (rs.next())
            {
                result.add(new CardExtended(
                        rs.getString("cardID"),
                        rs.getString("card_name"),
                        rs.getFloat("damage"),
                        rs.getBoolean("monsterCard"),
                        rs.getString("element")
                ));
            }
            return result;
        }
        catch (SQLException e)
        {
            return Collections.emptyList();
        }

    }

    public void receiveElo(String winner) {

        try {
            Connection con = Database.getConnection();
            PreparedStatement pstmt = con.prepareStatement(RECEIVE_ELO);
            pstmt.setString(1, winner);
            pstmt.execute();

            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void giveElo(String loser) {

        try {
            Connection con = Database.getConnection();
            PreparedStatement pstmt = con.prepareStatement(GIVE_ELO);
            pstmt.setString(1, loser);
            pstmt.execute();

            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addWin(String winner) {

        try {
            Connection con = Database.getConnection();
            PreparedStatement pstmt = con.prepareStatement(ADD_WIN);
            pstmt.setString(1, winner);
            pstmt.execute();

            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public void takeLoss(String loser) {

        try {
            Connection con = Database.getConnection();
            PreparedStatement pstmt = con.prepareStatement(TAKE_LOSS);
            pstmt.setString(1, loser);
            pstmt.execute();

            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
