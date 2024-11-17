package kurenkov.tutorservice.controllers.home.api;

import kurenkov.tutorservice.controllers.home.HomeController;
import kurenkov.tutorservice.entities.UserData;
import kurenkov.tutorservice.entities.dto.TutorDataDTO;
import kurenkov.tutorservice.mappers.TutorDataMapper;
import kurenkov.tutorservice.services.FavoriteService;
import kurenkov.tutorservice.services.UserDataService;
import kurenkov.tutorservice.services.VisitsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/home/api")
public class HomeRestController extends HomeController {

    @Autowired
    private UserDataService userDataService;

    @Autowired
    private FavoriteService favoriteService;

    @GetMapping("/recommended")
    public List<TutorDataDTO>  recommendedTutors() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            UserData currentUser = userDataService.loadUserDataByUsername(authentication.getName());
            return getRecommendations(currentUser.getId());
        }catch (Exception e){
            return new ArrayList<>();
        }
    }

    @GetMapping("/favorite/{tutorId}")
    public void favoriteTutors(@PathVariable Long tutorId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            UserData currentUser = userDataService.loadUserDataByUsername(authentication.getName());
            favoriteService.addFavoriteTutor(currentUser.getId(), tutorId);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    private List<TutorDataDTO> getRecommendations(Long userId) {
        List<UserData> tutorRecomends = userDataService.getUserIDbyTutor(userId);
        List<TutorDataDTO> recommendedTutors = new ArrayList<>();
        for (UserData tutor : tutorRecomends) {
            TutorDataDTO profile;
            profile = tutor != null ? getTutorProfileById(tutor.getId()) : null;
            var b = profile != null ? recommendedTutors.add(profile) : null;
        }
        return recommendedTutors;
    }

    private TutorDataDTO getTutorProfileById(Long id) {
        return TutorDataMapper.INSTANCE.userDataToTutorDataDTO(userDataService.getUserDataById(id));
    }


}
