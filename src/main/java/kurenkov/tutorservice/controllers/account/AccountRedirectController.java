package kurenkov.tutorservice.controllers.account;

import kurenkov.tutorservice.entities.Role;
import kurenkov.tutorservice.entities.UserData;
import kurenkov.tutorservice.services.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/getcurrentpage")
public class AccountRedirectController {

    @Autowired
    private UserDataService userDataService;


    @GetMapping
    private RedirectView getCurrentPage(){
        UserData currentUser = userDataService.loadUserDataByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName());
        if(currentUser.getUser().getRoles().stream().anyMatch(role -> role.getName().equals("TUTOR"))){
            return new RedirectView("/account/tutor");
        }else {
            return new RedirectView("/account/seeker");
        }
    }
}
