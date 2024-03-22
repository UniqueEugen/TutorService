package kurenkov.tutorservice.controllers.registry;

import kurenkov.tutorservice.entities.*;
import kurenkov.tutorservice.entities.dto.UserDataDTO;
import kurenkov.tutorservice.mappers.*;
import kurenkov.tutorservice.services.UserDataService;
import kurenkov.tutorservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private final UserDataService userDataService;

    @Autowired
    public RegistrationController(UserMapper userMapper, SeekerMapper seekerMapper,
                                  AddressMapper addressMapper, TutorMapper tutorMapper,
                                  UserDataMapper userDataMapper, UserDataService userDataService,
                                  UserService userService) {
        this.userDataService = userDataService;
    }

    @GetMapping
    public String showRegistrationPage() {
        return "registry/registrationPage";
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDataDTO userDataDTO) {
        try {
            UserData userData = UserDataMapper.INSTANCE.toUserDataFromDTO(userDataDTO);
            userDataService.saveUserData(userData);

            // Возвращаем успешный ответ
            return ResponseEntity.ok("Registration successful!");
        } catch (Exception e) {
            // Возвращаем ошибку с соответствующим кодом состояния
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed.");
        }
    }
}
