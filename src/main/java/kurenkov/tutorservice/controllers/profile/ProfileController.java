package kurenkov.tutorservice.controllers.profile;

import kurenkov.tutorservice.entities.*;
import kurenkov.tutorservice.entities.dto.FullTutorProfileDTO;
import kurenkov.tutorservice.entities.dto.TutorDataDTO;
import kurenkov.tutorservice.mappers.OrderMapper;
import kurenkov.tutorservice.mappers.TutorDataMapper;
import kurenkov.tutorservice.services.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private UserDataService userDataService;
    @Autowired
    private OrderService orderService;

    @Autowired
    private TutorService tutorService;

    @Autowired
    private SeekerService seekerService;

    @Autowired
    private TutorViewsService tutorViewsService;

    @GetMapping
    public String tut(Model model){
        model.addAttribute("profile", getTutorProfileById(25L));
        return "profile/Profile";
    }

    @GetMapping("/getTutor")
    public String tutorProfile(@RequestParam("id") Long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserData currentUser = userDataService.loadUserDataByUsername(authentication.getName());
        TutorDataDTO profile = getTutorProfileById(id);
        Long tutorId = getTutorId(id);
        ResponseEntity res = addTutorView(currentUser.getId(), tutorId);
        model.addAttribute("profile", profile);
        if (res.getStatusCode() == HttpStatus.OK){
            return "profile/Profile";
        } else {
            return "errors/error404";
        }
    }


    @GetMapping("/g")
    public String tuft(){
        return "profile/dgdg";
    }
    @PostMapping("/newOrder")
    public ResponseEntity<String> createOrder(@RequestBody OrderData orderData) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            UserData currentUser = userDataService.loadUserDataByUsername(authentication.getName());
            Order order = OrderMapper.INSTANCE.toOrder(orderData);
            currentUser.getSeeker().getSeekerOrders().add(order);
            UserData tutor = userDataService.getUserDataById(orderData.tutorId);
            tutor.getTutor().getTutorOrders().add(order);
            orderService.saveOrder(order);
            seekerService.saveSeeker(currentUser.getSeeker());
            tutorService.saveTutor(tutor.getTutor());
            String responseMessage = "The order has been successfully saved and is awaiting confirmation by the tutor!";
            return ResponseEntity.ok()
                    .header("Content-Type", "text/plain")
                    .body(responseMessage);
        }catch (Exception e) {
            // Возвращаем ошибку с соответствующим кодом состояния
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("Content-Type",
                    "text/plain").body("Order creation failed");
        }

    }

    private TutorDataDTO getTutorProfileById(Long id) {
        return TutorDataMapper.INSTANCE.userDataToTutorDataDTO(userDataService.getUserDataById(id));
    }
    private Long getTutorId(Long id) {
        UserData userData = userDataService.getUserDataById(id);
        Long tutorId = userData.getTutor().getId();
        return tutorId;
    }

    public ResponseEntity addTutorView(Long userId, Long tutorId) {
        tutorViewsService.addOrUpdateTutorView(userId, tutorId);
        return ResponseEntity.ok().build();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderData {
        private Long tutorId;
        private Statuses status = Statuses.PENDING;
        private String date;
        private String time;

    }
}
