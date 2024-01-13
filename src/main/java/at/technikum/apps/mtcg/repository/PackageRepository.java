package at.technikum.apps.mtcg.repository;

import at.technikum.apps.mtcg.entity.cards.Card;
import at.technikum.apps.task.data.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public List<Integer> getAvailablePackages() {

        String GET_AVAILABLE_PACKAGES = "SELECT packageID FROM package WHERE available = true;";

        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(GET_AVAILABLE_PACKAGES);
        ) {

            ResultSet rs = pstmt.executeQuery();

            List<Integer> packageIds = new ArrayList<>();
            ;
            while (rs.next()) {
                packageIds.add(rs.getInt("packageID"));
            }

            return packageIds;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


    public List<Card> buyPackage(String username, int coins, int id) throws SQLException { //PackageID !!!

        // 1. Select Cardids from packages
        // 2. Get Card (id,name,damage) from Cardids //1.
        // 3. give 5 coins
        // 4. Set Card_Holder

        String CARDIDS_FROM_PACKAGE = "SELECT card1, card2, card3, card4, card5 FROM package WHERE packageID = ?";
        String GET_CARD = "SELECT cardID, card_name, damage FROM card WHERE cardID = ?";
        String GIVE_FIVE_COINS = "UPDATE user_session SET coins = coins - 5 WHERE username = ?";
        String BUY_PACKAGE = "UPDATE card SET card_holder = ? WHERE cardID = ? ";
        String SET_PACKAGE_UNAVAILABLE = "UPDATE package SET available = false WHERE packageID = ? ";


        try (Connection con = Database.getConnection()) {

            //1. Select cardids
            PreparedStatement pstmt = con.prepareStatement(CARDIDS_FROM_PACKAGE);

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            List<String> cardIds;
            cardIds = new ArrayList<>();
            rs.next();
            cardIds.add(rs.getString("card1"));
            cardIds.add(rs.getString("card2"));
            cardIds.add(rs.getString("card3"));
            cardIds.add(rs.getString("card4"));
            cardIds.add(rs.getString("card5"));


            List<Card> cards = new ArrayList<>();

            //2. Get Card
            try (
                    PreparedStatement pstmt2 = con.prepareStatement(GET_CARD);
            ) {
                for (String ids : cardIds) {
                    pstmt2.setString(1, ids);
                    ResultSet rs2 = pstmt2.executeQuery();
                    rs2.next();
                    cards.add(new Card(rs2.getString("cardID"), rs2.getString("card_name"), rs2.getInt("damage")));

                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            //3. give 5 coins
            try (
                    PreparedStatement pstmt3 = con.prepareStatement(GIVE_FIVE_COINS);
            ) {
                pstmt3.setString(1, username);
                pstmt3.executeUpdate();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


            //4. set card_holder

            try (
                    PreparedStatement pstmt4 = con.prepareStatement(BUY_PACKAGE);
            ) {
                for (Card card : cards) {
                    pstmt4.setString(1, username);
                    pstmt4.setString(2, card.getId());

                    pstmt4.executeUpdate();
                }

                PreparedStatement pstmt5 = con.prepareStatement(SET_PACKAGE_UNAVAILABLE);
                pstmt5.setInt(1, id);
                pstmt5.execute();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            return cards;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}