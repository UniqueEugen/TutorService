package kurenkov.tutorservice.controllers.home;

import kurenkov.tutorservice.entities.UserData;
import kurenkov.tutorservice.entities.dto.TutorDataDTO;
import kurenkov.tutorservice.mappers.TutorDataMapper;
import kurenkov.tutorservice.services.TutorService;
import kurenkov.tutorservice.services.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class HomeRestController extends HomeController{

    @Autowired
    private UserDataService userDataService;


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

    private List<TutorDataDTO> getRecommendations(Long userId) {
        List<UserData> tutorRecomends = userDataService.getUserIDbyTutor(userId);
        List<TutorDataDTO> recommendedTutors = new ArrayList<>();
        for (UserData tutor : tutorRecomends) {
            TutorDataDTO profile = getTutorProfileById(tutor.getId());
            recommendedTutors.add(profile);
        }
        return recommendedTutors;
    }

    private TutorDataDTO getTutorProfileById(Long id) {
        return TutorDataMapper.INSTANCE.userDataToTutorDataDTO(userDataService.getUserDataById(id));
    }
}
