package at.technikum.apps.mtcg.repository;

import at.technikum.apps.mtcg.entity.Token;
import at.technikum.apps.mtcg.entity.User;
import at.technikum.apps.database.data.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SessionRepository {

    private final String GET_TOKEN = "SELECT token FROM user_session WHERE username = ?";
    private final String SET_TOKEN = "UPDATE user_session SET token = ? WHERE username = ?";

    private final Database database = new Database();


    public Token login(String username) throws SQLException {


        Connection con = Database.getConnection();
        PreparedStatement pstmt = con.prepareStatement(SET_TOKEN);

        pstmt.setString(1, username + "-mtcgToken");
        pstmt.setString(2, username);
        pstmt.execute();

        try (
                PreparedStatement pstmt1 = con.prepareStatement(GET_TOKEN)
        ) {
            pstmt1.setString(1, username);
            ResultSet rs = pstmt1.executeQuery();

            if (rs.next()) {
                return new Token(
                        rs.getString("token")
                );
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // Find user by username
    public User getUser(String username){

        final String FIND_USER = "SELECT * FROM user_session WHERE username = ?";

        try (
                Connection con = Database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(FIND_USER);

        ) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getString("username"),
                        rs.getString("password")
                );
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
