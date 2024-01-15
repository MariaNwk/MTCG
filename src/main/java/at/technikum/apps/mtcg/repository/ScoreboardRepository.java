package at.technikum.apps.mtcg.repository;

import at.technikum.apps.mtcg.entity.UserStat;
import at.technikum.apps.database.data.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScoreboardRepository {

    private final String GET_SCOREBOARD = "SELECT * FROM stats ORDER BY elo DESC";

    private final Database database = new Database();
    public List<UserStat> getScoreboard() {

        try
        {
            Connection con = Database.getConnection();
            PreparedStatement pstmt = con.prepareStatement(GET_SCOREBOARD);
            ResultSet rs = pstmt.executeQuery();
            con.close();

            List<UserStat> stats = new ArrayList<>();
            while (rs.next())
            {
                stats.add(new UserStat(
                        rs.getString("name"),
                        rs.getInt("elo"),
                        rs.getInt("wins"),
                        rs.getInt("losses")
                ));
            }
            return stats;
        }
        catch (SQLException e)
        {
            return Collections.emptyList();
        }


    }
}
