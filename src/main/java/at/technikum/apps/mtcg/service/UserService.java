package at.technikum.apps.mtcg.service;

import at.technikum.apps.mtcg.entity.User;
import at.technikum.apps.mtcg.entity.UserData;
import at.technikum.apps.mtcg.repository.UserRepository;
import at.technikum.server.http.HttpStatus;
import java.util.List;
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
            return Optional.ofNullable(userRepository.findUserData(username));
    }


    public UserData updateUser(String username, UserData userdata){
         return userRepository.updateUser(username, userdata);
    }


}
