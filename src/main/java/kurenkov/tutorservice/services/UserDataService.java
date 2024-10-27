package kurenkov.tutorservice.services;

import kurenkov.tutorservice.entities.dto.TutorDataDTO;
import kurenkov.tutorservice.repositories.UserDataRepository;
import kurenkov.tutorservice.entities.UserData;
import kurenkov.tutorservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDataService {

    private final UserDataRepository userDataRepository;

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final TutorService tutorService;

    @Autowired
    public UserDataService(UserDataRepository userDataRepository,
                           UserService userService,
                           PasswordEncoder passwordEncoder,
                           TutorService tutorService) {

        this.userDataRepository = userDataRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.tutorService = tutorService;
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

    public List<TutorDataDTO> namesAndSurnamesWithTutor() { return (userDataRepository.findNamesAndSurnamesWithTutor());}

    public UserData loadUserDataByUsername(String username){
        return getUserDataById(userService.findUserByUsername(username).getId());
    }

    public List<UserData>  getUserIDbyTutor(Long userId){
        List<Long> ids = tutorService.getTutorsIds(userId);
        List<UserData> recommneds = new ArrayList<>();
        for(Long id : ids){
            recommneds.add(userDataRepository.findByTutor(tutorService.getTutorById(id)));
        }
       return recommneds;
    }
}
