package kurenkov.tutorservice.controllers.registry;

import kurenkov.tutorservice.entities.*;
import kurenkov.tutorservice.mappers.*;
import kurenkov.tutorservice.services.UserDataService;
import kurenkov.tutorservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private final UserMapper userMapper;
    private final SeekerMapper seekerMapper;
    private final AddressMapper addressMapper;
    private final TutorMapper tutorMapper;
    private final UserDataMapper userDataMapper;
    private final UserDataService userDataService;
    private final UserService userService;

    @Autowired
    public RegistrationController(UserMapper userMapper, SeekerMapper seekerMapper,
                                  AddressMapper addressMapper, TutorMapper tutorMapper,
                                  UserDataMapper userDataMapper, UserDataService userDataService,
                                  UserService userService) {
        this.userMapper = userMapper;
        this.seekerMapper = seekerMapper;
        this.addressMapper = addressMapper;
        this.tutorMapper = tutorMapper;
        this.userDataMapper = userDataMapper;
        this.userDataService = userDataService;
        this.userService = userService;
    }

    @GetMapping
    public String showRegistrationPage() {
        return "registry/registrationFirstChapter";
    }

    @PostMapping("/registry")
    public String registerUser(@RequestParam("user.login") String login,
                               @RequestParam("user.password") String password,
                               @RequestParam("role") String role,
                               @RequestParam("userData.name") String name,
                               @RequestParam("userData.surname") String surname,
                               @RequestParam(value = "userData.secName", required = false) String secName,
                               @RequestParam("userData.eMail") String email,
                               @RequestParam("userData.phone") String phone,
                               @RequestParam(value = "seeker.age", required = false) Integer age,
                               @RequestParam(value = "seeker.description", required = false) String description,
                               @RequestParam(value = "tutor.specialisation", required = false) String specialisation,
                               @RequestParam(value = "tutor.price", required = false) Integer price,
                               @RequestParam(value = "tutor.photo", required = false) MultipartFile photo,
                               @RequestParam(value = "address.country", required = false) String country,
                               @RequestParam(value = "address.city", required = false) String city,
                               @RequestParam(value = "address.street", required = false) String street,
                               @RequestParam(value = "address.house", required = false) String house,
                               @RequestParam(value = "address.office", required = false) String office) {
        User user = userMapper.toUser(login, password);
        userService.saveUser(user);
        Seeker seeker = null;
        Address address;
        Tutor tutor = null;
        if (role.equals("seeker")){
            seeker = seekerMapper.toSeeker(age, description);
        }else {
            address=addressMapper.toAddress(country, city, street,house, office);
            tutor = tutorMapper.toTutor(specialisation, price, photo, address);
        }
        UserData userData = userDataMapper.toUserData(name, surname, secName, email, phone, tutor, seeker);
        userDataService.saveUserData(userData);
        return "registration-success"; // Return the name of the success page template
    }

}
