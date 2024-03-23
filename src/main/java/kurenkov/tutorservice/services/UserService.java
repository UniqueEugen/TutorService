package kurenkov.tutorservice.services;

import kurenkov.tutorservice.repositories.UserRepository;
import kurenkov.tutorservice.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository usersRepository;

    public UserService(UserRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public List<User> getAllUsers() {
        return usersRepository.findAll();
    }

    public User getUserById(Long id) {
        return usersRepository.findById(id).orElse(null);
    }

    public User saveUser(User user) {
        return usersRepository.save(user);
    }

    public void deleteUser(Long id) {
        usersRepository.deleteById(id);
    }

    public boolean isUserLoginExists(String login) {
        return usersRepository.existsByLogin(login);
    }
}
