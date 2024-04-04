package kurenkov.tutorservice.controllers.account;
import kurenkov.tutorservice.entities.Address;
import kurenkov.tutorservice.entities.Tutor;
import kurenkov.tutorservice.entities.User;
import kurenkov.tutorservice.entities.UserData;
import kurenkov.tutorservice.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/account/tutor")
public class TutorAccountController {

    @Autowired
    private UserDataService userDataService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private UserService userService;

    @Autowired
    private TutorService tutorService;

    @Autowired
    private PhotoService photoService;

    private String username;

    // Обработка GET-запроса для отображения страницы личного кабинета
    @GetMapping
    public String showAccountPage(Model model) {
        username = SecurityContextHolder.getContext().getAuthentication().getName();
        // Здесь можно добавить логику для получения данных аккаунта из базы данных или другого источника
        UserData user = getAccount(); // Ваша логика получения аккаунта
        model.addAttribute("account", user);
        return "account/tutorAccountPage"; // Возвращаем имя шаблона Thymeleaf
    }
    // Обработка POST-запроса для обновления логина и пароля
    @PostMapping("/setuser")
    public ResponseEntity<String> updateLoginAndPassword(@RequestBody User user) {
        try{
            // Здесь можно добавить логику для обновления логина и пароля в базе данных или другом хранилище
            userService.saveUser(user.updateUserData(getAccount().getUser())); // Ваша логика обновления логина и пароля
            String responseMessage = "Successfully changed!";
            return ResponseEntity.ok()
                    .header("Content-Type", "text/plain")
                    .body(responseMessage);
        }catch (Exception e) {
            // Возвращаем ошибку с соответствующим кодом состояния
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("Content-Type",
                    "text/plain").body("Change failed!");
        }
    }

    // Обработка POST-запроса для обновления персональных данных
    @PostMapping("/setuserdata")
    public String updateUserData(@RequestBody UserData userData) {
        // Здесь можно добавить логику для обновления персональных данных в базе данных или другом хранилище
        userDataService.saveUserData(getAccount().updateUserData(userData));// Ваша логика обновления персональных данных
        return "redirect:/account/profile"; // Перенаправляем обратно на страницу личного кабинета
    }

    // Обработка POST-запроса для обновления данных адреса
    @PostMapping("/setaddress")
    public String updateAddressData(@RequestBody Address address) {
        // Здесь можно добавить логику для обновления данных адреса в базе данных или другом хранилище
        addressService.saveAddress(getAccount().getTutor().getAddress().updateAdress(address)); // Ваша логика обновления данных адреса
        return "redirect:/account/profile"; // Перенаправляем обратно на страницу личного кабинета
    }

    @PostMapping("/setTutor")
    public String updateTutorData(@RequestBody Tutor tutor){
        tutorService.saveTutor(getAccount().getTutor().updateTutorData(tutor));
        return "redirect:/account/profile";
    }

    @PostMapping("/uploadPhoto")
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile image){
        String responseMessage = "Successfully changed!";
        if (!image.isEmpty()) {
            try {
                photoService.savePhoto(image, getAccount().getTutor());
                return ResponseEntity.ok()
                        .header("Content-Type", "text/plain")
                        .body(responseMessage);
            } catch (Exception e) {
                // Обработка ошибок
                // ...
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("Content-Type",
                        "text/plain").body("Change failed!");
            }
        } else {
            // Обработка случая, когда изображение не было выбрано
            // ...
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("Content-Type",
                    "text/plain").body("Change failed!");
        }

    }

    private UserData getAccount() {
        return userDataService.loadUserDataByUsername(username);
    }

}
