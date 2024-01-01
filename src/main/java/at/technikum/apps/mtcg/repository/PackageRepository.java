package at.technikum.apps.mtcg.repository;

import at.technikum.apps.mtcg.entity.Package;
import at.technikum.apps.mtcg.entity.User;
import at.technikum.apps.mtcg.entity.cards.Card;
import at.technikum.apps.task.data.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PackageRepository {

    private final Database database = new Database();


    public void save(Card[] cards) {

        String SAVE_PACKAGE_IN_DB = "INSERT INTO package (card1, card2, card3, card4, card5) VALUES (?,?,?,?,?)";

        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(SAVE_PACKAGE_IN_DB);
        ) {
            pstmt.setString(1, cards[0].getId());
            pstmt.setString(2, cards[1].getId());
            pstmt.setString(3, cards[2].getId());
            pstmt.setString(4, cards[3].getId());
            pstmt.setString(5, cards[4].getId());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


}
