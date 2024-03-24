package kurenkov.tutorservice.services;

import kurenkov.tutorservice.repositories.UserDataRepository;
import kurenkov.tutorservice.entities.UserData;
import kurenkov.tutorservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDataService {

    private final UserDataRepository userDataRepository;

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserDataService(UserDataRepository userDataRepository,
                           UserService userService,
                           PasswordEncoder passwordEncoder) {

        this.userDataRepository = userDataRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserData> getAllUserData() {
        return userDataRepository.findAll();
    }

    public UserData getUserDataById(Long id) {
        return userDataRepository.findById(id).orElse(null);
    }

    public UserData saveUserData(UserData userData) {
        String password = passwordEncoder.encode(userData.getUser().getPassword());
        userData.getUser().setPassword(password);

        return userDataRepository.save(userData);
    }

    public void deleteUserData(Long id) {
        userDataRepository.deleteById(id);
    }

    public boolean isUserLoginExists(String login){
        return userService.isUserLoginExists(login);
    }
}
