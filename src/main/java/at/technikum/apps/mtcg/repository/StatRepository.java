package at.technikum.apps.mtcg.repository;

import at.technikum.apps.mtcg.entity.UserStat;
import at.technikum.apps.database.data.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class StatRepository {



    private final String GET_USER_STATS = "SELECT * FROM stats WHERE name = ?";


    private final Database database = new Database();

    public Optional<UserStat> retrieveStats(String username) {


        Optional<UserStat> userStat;

        try
        {
            Connection con = Database.getConnection();
            PreparedStatement pstmt = con.prepareStatement(GET_USER_STATS);
            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();
            con.close();
            if (rs.next())
            {
                userStat = Optional.of(new UserStat(
                        rs.getString("name"),
                        rs.getInt("elo"),
                        rs.getInt("wins"),
                        rs.getInt("losses")
                ));
                return userStat;
            }
            return Optional.empty();
        }
        catch (SQLException e)
        {
            return Optional.empty();
        }

    }

}
