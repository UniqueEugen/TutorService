package kurenkov.tutorservice.services;

import kurenkov.tutorservice.repositories.RoleRepository;
import kurenkov.tutorservice.repositories.UserRepository;
import kurenkov.tutorservice.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository usersRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository usersRepository,
                       PasswordEncoder passwordEncoder) {

        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return usersRepository.findAll();
    }

    public User getUserById(Long id) {
        return usersRepository.findById(id).orElse(null);
    }

    public User saveUser(User user) {
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);

        return usersRepository.save(user);
    }

    public void deleteUser(Long id) {
        usersRepository.deleteById(id);
    }

    public boolean isUserLoginExists(String login) {

        return usersRepository.existsByLogin(login);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usersRepository.findByLogin(username);
    }

    public User findUserByUsername(String username){
        return usersRepository.findByLogin(username);
    }
}
