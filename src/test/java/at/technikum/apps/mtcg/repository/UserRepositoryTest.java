package at.technikum.apps.mtcg.repository;

import at.technikum.apps.mtcg.entity.User;
import at.technikum.apps.mtcg.entity.UserData;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.OngoingStubbing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserRepositoryTest {

    @Test
    void save() throws SQLException {

        UserRepository userRepository = new UserRepository();
        User testUser = new User("testuser", "testpassword");
        // Test
        User savedUser = userRepository.save(testUser);

        // Verification
        assertEquals(testUser, savedUser);

    }

    @Test
    void updateUserData() throws SQLException {


                UserRepository userRepository = new UserRepository();

                // Test data
                String testUsername = "testuser";
                UserData testUserData = new UserData("John Doe", "A bio", "profile.jpg");

                // Test
                UserData updatedUserData = userRepository.updateUser(testUsername, testUserData);

                // Verification
                assertEquals(testUserData, updatedUserData);

                
    }



}