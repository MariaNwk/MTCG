package at.technikum.apps.mtcg.repository;

import at.technikum.apps.mtcg.entity.User;
import at.technikum.apps.mtcg.entity.cards.Card;
import at.technikum.apps.mtcg.entity.cards.CardExtended;
import at.technikum.apps.task.data.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CardRepository {


    private final String CREATE_CARD = "INSERT INTO card (cardID, card_name, damage, monsterCard, element) VALUES (?,?,?,?,?)";
    private final String SHOW_CARDS_FROM_USER = "SELECT cardID, card_name, damage FROM card WHERE card_holder = ?";

    private final Database database = new Database();


    public void createCard(CardExtended card) {

        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(CREATE_CARD)
        ) {
            pstmt.setString(1, card.getCardId());
            pstmt.setString(2, card.getCard_name());
            pstmt.setFloat(3, card.getDamage());
            pstmt.setBoolean(4, card.isMonsterCard() );
            pstmt.setString(5, card.getElement());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Card> showCardsFromUser(String username) { //card_holder = username

        List <Card> cards = new ArrayList<>();

        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(SHOW_CARDS_FROM_USER);

        ) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Card card = new Card(
                        rs.getString("cardID"),
                        rs.getString("card_name"),
                        rs.getFloat("damage")
                );
                cards.add(card);
            }

            return cards;

        } catch (SQLException e) {
            return cards;
        }


    }

}


