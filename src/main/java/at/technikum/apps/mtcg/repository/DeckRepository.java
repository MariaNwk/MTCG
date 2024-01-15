package at.technikum.apps.mtcg.repository;

import at.technikum.apps.mtcg.entity.cards.Card;
import at.technikum.apps.database.data.Database;
import at.technikum.server.http.HttpStatus;
import at.technikum.server.http.Response;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class DeckRepository {

    //SHOW DECK
    private final String SHOW_DECK = "SELECT cardID, card_name, damage FROM card WHERE cardID = ?";
    private final String CARDIDS_FROM_DECK = "SELECT card1, card2, card3, card4 FROM deck WHERE deckID = ?";

    //CREATE DECK
    private final String GET_CARD_HOLDER = "SELECT card_holder FROM card WHERE cardID = ?";
    private final String CREATE_DECK = "INSERT INTO deck VALUES (?,?,?,?,?)";




    private final Database database = new Database();
    public List<Card> showDeck(String username) {


        try (
                Connection con = database.getConnection();
        ) {

            //Get Cardids from deck

            PreparedStatement pstmt = con.prepareStatement(CARDIDS_FROM_DECK);

            pstmt.setString(1,username);
            ResultSet rs = pstmt.executeQuery();

            List<String> cardIds;
            cardIds = new ArrayList<>();
            while (rs.next()) {
                cardIds.add(rs.getString("card1"));
                cardIds.add(rs.getString("card2"));
                cardIds.add(rs.getString("card3"));
                cardIds.add(rs.getString("card4"));
            }

            if (cardIds.isEmpty()) {
                return Collections.emptyList(); // Return an empty list when no cards are found
            }



            //Get Cards with id
            List<Card> cards = new ArrayList<>();

            try (
                    PreparedStatement pstmt2 = con.prepareStatement(SHOW_DECK);
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

            return cards;

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public Response configureDeck(String username, String[] cards) throws SQLException {


       try(Connection con = database.getConnection();) {


           //Get card_holder and check if card_holder exists for the deck

           PreparedStatement pstmt = con.prepareStatement(GET_CARD_HOLDER);

           Optional<String> card_holder = Optional.empty();

           for (String id : cards) {

               pstmt.setString(1, id);
               ResultSet rs = pstmt.executeQuery();

               if (rs.next()) {
                   card_holder = Optional.ofNullable(rs.getString("card_holder"));
               } else {
                   card_holder = Optional.empty();
               }


               if (card_holder.isEmpty() || !card_holder.orElse(null).equals(username)) {
                   Response response = new Response();
                   response.setStatus(HttpStatus.DOES_NOT_BELONG_USER);
                   return response;
               }

           }


           //createdeck


           try (PreparedStatement pstmt2 = con.prepareStatement(CREATE_DECK)) {

               pstmt2.setString(1, username);
               pstmt2.setString(2, cards[0]);
               pstmt2.setString(3, cards[1]);
               pstmt2.setString(4, cards[2]);
               pstmt2.setString(5, cards[3]);

               pstmt2.execute();


               Response response = new Response();
               response.setStatus(HttpStatus.DECK_HAS_CARDS);
               return response;
           } catch (SQLException e) {
               throw new RuntimeException(e);
           }



       }catch (SQLException e){
           throw new RuntimeException(e);
       }

    }

}
