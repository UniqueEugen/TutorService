package kurenkov.tutorservice.controllers.home;

import jakarta.servlet.http.HttpServletRequest;
import kurenkov.tutorservice.entities.dto.TutorDataDTO;
import kurenkov.tutorservice.entities.dto.TutorDataDTOFav;
import kurenkov.tutorservice.mappers.TutorDataMapper;
import kurenkov.tutorservice.services.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
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
        boolean isAuth = !username.equals("anonymousUser");
        System.out.println(isAuth);
        model.addAttribute("auth",isAuth);
        System.out.println(username);
        List<TutorDataDTO> tutors = TutorDataMapper.INSTANCE.userDataListToTutorDataDTOList(userDataService.getAllUserData());
        //List<Tutor> tutors = tutorService.getAllTutors();
        List<TutorDataDTOFav> tutorsFav = addFavorite(tutors);
        model.addAttribute("tutors", tutorsFav);
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

    private List<TutorDataDTOFav> addFavorite(List<TutorDataDTO> allTutors){
        List<TutorDataDTOFav> favoriteTutors = new ArrayList<>();
        for(TutorDataDTO tutor : allTutors){
            TutorDataDTOFav favoriteTutor = new TutorDataDTOFav(tutor, true);
            favoriteTutors.add(favoriteTutor);
        }
        return favoriteTutors;
    }
}
