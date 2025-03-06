package kurenkov.tutorservice.controllers.serviceControllers;

import kurenkov.tutorservice.entities.Role;
import kurenkov.tutorservice.entities.User;
import kurenkov.tutorservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/service")
public class ShutdownController {

    @Autowired
    private UserService userService;

    private final ConfigurableApplicationContext context;

    public ShutdownController(ConfigurableApplicationContext context) {
        this.context = context;
    }

    @RequestMapping
    public String getContacts() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUsername(authentication.getName());
        System.out.println(user.getRoles().iterator().next().getName());
        System.out.println("Цикл");
        for (Role role : user.getRoles()) {
            System.out.println(role.getName());
        }
        System.out.println(user.getRoles().stream().filter(role -> role.getName().equals("ADMIN")).count() > 0);
        if (user.getRoles().stream().filter(role -> role.getName().equals("ADMIN")).count() > 0) {
            return "service/ShutdownPage";
        }else {
            return "errors/error403";
        }
    }

    @GetMapping("/shutdown")
    public String shutdown() {
        SpringApplication.exit(context);
        return "Application is shutting down...";
    }
}