package kurenkov.tutorservice.controllers;

import kurenkov.tutorservice.entities.User;
import kurenkov.tutorservice.services.UserDataService;
import kurenkov.tutorservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    UserService userService;

    @GetMapping
    public RedirectView redirectToHomePage() {
        /*User user = userService.getUserById(53L);
        user.setPassword("1234");
        userService.saveUser(user);*/
        return new RedirectView("/home");
    }
}
