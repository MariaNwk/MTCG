package at.technikum.apps.mtcg.repository;

import at.technikum.apps.mtcg.entity.cards.CardExtended;
import at.technikum.apps.task.data.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CardRepository {

    private final String CREATE_CARD = "INSERT INTO card (cardID, card_name, damage, monsterCard, element) VALUES (?,?,?,?,?)";
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
}


