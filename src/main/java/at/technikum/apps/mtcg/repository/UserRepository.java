package at.technikum.apps.mtcg.repository;

import at.technikum.apps.mtcg.entity.UserData;
import at.technikum.apps.mtcg.entity.User;
import at.technikum.apps.database.data.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserRepository {


    //CREATE USER

    private final String SAVE_SQL = "INSERT INTO user_session(Username, Password) VALUES(?, ?)";
    private final String CREATE_EMPTY_USER_STATS = "INSERT INTO stats (name) VALUES (?)";
    private final String GET_COINS = "SELECT coins FROM user_session WHERE username = ?";


    //USERDATA
    private final String GET_USERDATA_BY_USERNAME = "SELECT * FROM user_data WHERE username = ?";
    private final String UPDATE_USERDATA_BY_USERNAME = "INSERT INTO user_data (username, name, bio, image) VALUES (?, ?, ?, ?) ON CONFLICT (username) DO UPDATE SET name = EXCLUDED.name, bio = EXCLUDED.bio, image = EXCLUDED.image";



    private final Database database = new Database();





    //POST Register  user
    public User save(User user) {

        try (
                Connection con = Database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(SAVE_SQL)
        ) {


            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.execute();


            //Stats

            PreparedStatement pstmt2 = con.prepareStatement(CREATE_EMPTY_USER_STATS);

            pstmt2.setString(1, user.getUsername());
            pstmt2.execute();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return user;

    }



    //GET userdata: name, image, bio
    public Optional<UserData> getUserData(String username){

        Optional<UserData> userData;
        try
        {
            Connection con = Database.getConnection();
            PreparedStatement pstmt = con.prepareStatement(GET_USERDATA_BY_USERNAME);
            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();
            con.close();
            if (rs.next())
            {
                userData = Optional.of(new UserData(
                        rs.getString("name"),
                        rs.getString("bio"),
                        rs.getString("image")
                ));

                return userData;
            }
            return Optional.empty();
        }
        catch (SQLException e)
        {
            return Optional.empty();
        }



    }

    //PUT update name, bio, image
    public UserData updateUser(String username, UserData userdata) {

        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(UPDATE_USERDATA_BY_USERNAME)
        ) {

            pstmt.setString(1, username);
            pstmt.setString(2, userdata.getName());
            pstmt.setString(3, userdata.getBio());
            pstmt.setString(4, userdata.getImage());

            pstmt.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userdata;
    }



    //PACKAGE

    public int getCoins(String username) {
        try (
                Connection con = Database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(GET_COINS)
        ) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            return rs.getInt("coins");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}

