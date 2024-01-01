package at.technikum.apps.mtcg.repository;

import at.technikum.apps.mtcg.entity.Token;
import at.technikum.apps.mtcg.entity.UserData;
import at.technikum.apps.mtcg.entity.User;
import at.technikum.apps.task.data.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository {

    private final String FIND_ALL_SQL = "SELECT * FROM user_session";
    private final String SAVE_SQL = "INSERT INTO user_session(Username, Password) VALUES(?, ?)";
    private final String GET_USER_BY_USERNAME = "SELECT * FROM user_session WHERE username = ?";
    private final String GET_USERDATA_BY_USERNAME = "SELECT * FROM user_data WHERE username = ?";
    private final String GET_TOKEN = "SELECT token FROM user_session WHERE username = ?";
    private final String SET_TOKEN = "UPDATE user_session SET token = ? WHERE username = ?";
    private final String UPDATE_USERDATA_BY_USERNAME = "INSERT INTO user_data (username, name, bio, image) VALUES (?, ?, ?, ?) " +
            "ON CONFLICT (username) DO UPDATE SET name = EXCLUDED.name, bio = EXCLUDED.bio, image = EXCLUDED.image";
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

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return user;

    }


    // Overview of Data, not required!!!
    public List<User> findAll()
    {
        List<User> users = new ArrayList<>();

        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(FIND_ALL_SQL);
                ResultSet rs = pstmt.executeQuery()
        ) {
            while (rs.next()) {
                User user = new User(
                        rs.getString("username"),
                        rs.getString("password")
                );
                users.add(user);
            }

            return users;

        } catch (SQLException e) {
            return users;
        }
    }



    //GET userdata: name, image, bio
    public UserData findUserData(String username){
        try (
                Connection con = Database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(GET_USERDATA_BY_USERNAME)
        ) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new UserData(
                        rs.getString("username"),
                        rs.getString("name"),
                        rs.getString("bio"),
                        rs.getString("image")
                );
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //PUT update name, bio, image
    public UserData updateUser(String username, UserData userdata) {

        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(UPDATE_USERDATA_BY_USERNAME)
        ) {
            pstmt.setString(1, userdata.getUsername());
            pstmt.setString(2, userdata.getName());
            pstmt.setString(3, userdata.getBio());
            pstmt.setString(4, userdata.getImage());

            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                return userdata;
            } else {
                throw new RuntimeException("Update failed. User not found.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //------------------------------------------------------------
    //------------------------------------------------------------
    //LOGIN

    // Find user by username
    public User findUser(String username){

        try (
                Connection con = Database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(GET_USER_BY_USERNAME);

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

    public void login(String username) throws SQLException {


            Connection con = Database.getConnection();
            PreparedStatement pstmt = con.prepareStatement(SET_TOKEN);

            pstmt.setString(1, username + "-mtcgToken");
            pstmt.setString(2, username);
            pstmt.execute();


    }

    public Token getTokenFromUser(String username){
        try (
                Connection con = Database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(GET_TOKEN)
        ) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

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


    public Token authenticate(User user) throws SQLException {

        User userDB = findUser(user.getUsername());

        if(userDB == null || !userDB.getPassword().equals(user.getPassword())){
            throw new SQLException("Invalid username/password provided");
        }

            login(userDB.getUsername());
            return getTokenFromUser(userDB.getUsername());

    }

}








/*

    public List<User> findAll() {

        List<User> users = new ArrayList<>();

        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(FIND_ALL_SQL);
                ResultSet rs = pstmt.executeQuery()
        ) {
            while (rs.next()) {
                User user = new User(

                        rs.getString("id"),
                        rs.getString("username"),
                        rs.getString("password")
                );
                users.add(user);
            }

            return users;
        } catch (SQLException e) {
            return users;
        }
    }


    //GET user by username
    public Optional<User> findUserByUsername(String username){
        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(GET_USER_BY_USERNAME)
        ) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return Optional.of(new User(
                        rs.getString("id"),
                        rs.getString("username"),
                        rs.getString("password")
                ));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    */