package at.technikum.apps.mtcg.service;

import at.technikum.apps.mtcg.entity.User;
import at.technikum.apps.mtcg.entity.UserData;
import at.technikum.apps.mtcg.repository.UserRepository;
import at.technikum.server.http.HttpStatus;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


public class UserService {


        private final UserRepository userRepository;

        public UserService(UserRepository userRepository) {
            this.userRepository = new UserRepository();
        }



        public User save(User user) {
                return userRepository.save(user);
        }

        public List<User> findAll(){
            return userRepository.findAll();
        }


    public Optional<UserData> getUserData(String username){
            return userRepository.getUserData(username);
    }


    public void updateUser(String username, UserData userdata){
        userRepository.updateUser(username, userdata);
    }


}
