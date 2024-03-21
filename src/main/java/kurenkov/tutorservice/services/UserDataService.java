package kurenkov.tutorservice.services;

import kurenkov.tutorservice.repositories.UserDataRepository;
import kurenkov.tutorservice.entities.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDataService {

    private final UserDataRepository userDataRepository;

    public UserDataService(UserDataRepository userDataRepository) {
        this.userDataRepository = userDataRepository;
    }

    public List<UserData> getAllUserData() {
        return userDataRepository.findAll();
    }

    public UserData getUserDataById(Long id) {
        return userDataRepository.findById(id).orElse(null);
    }

    public UserData saveUserData(UserData userData) {
        return userDataRepository.save(userData);
    }

    public void deleteUserData(Long id) {
        userDataRepository.deleteById(id);
    }
}
