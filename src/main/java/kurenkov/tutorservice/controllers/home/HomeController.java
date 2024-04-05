package kurenkov.tutorservice.controllers.home;

import jakarta.servlet.http.HttpServletRequest;
import kurenkov.tutorservice.entities.dto.TutorDataDTO;
import kurenkov.tutorservice.mappers.TutorDataMapper;
import kurenkov.tutorservice.services.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    /*@Autowired
    private TutorService tutorService;*/

    @Autowired
    private UserDataService userDataService;

    @GetMapping
    public String getTutors(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        System.out.println(username);
        List<TutorDataDTO> tutors = TutorDataMapper.INSTANCE.userDataListToTutorDataDTOList(userDataService.getAllUserData());
        //List<Tutor> tutors = tutorService.getAllTutors();
        System.out.println(tutors);
        model.addAttribute("tutors", tutors);
        return "homePage";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            request.getSession().invalidate();
        }
        return "redirect:/";
    }
}
