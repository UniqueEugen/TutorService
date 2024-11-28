package kurenkov.tutorservice.controllers.analys.api;

import kurenkov.tutorservice.entities.Order;
import kurenkov.tutorservice.entities.UserData;
import kurenkov.tutorservice.entities.dto.OrderDataDTO;
import kurenkov.tutorservice.entities.dto.SeekerDataDTO;
import kurenkov.tutorservice.entities.dto.TutorDataDTO;
import kurenkov.tutorservice.mappers.OrderListMapper;
import kurenkov.tutorservice.mappers.TutorDataMapper;
import kurenkov.tutorservice.services.OrderService;
import kurenkov.tutorservice.services.TutorService;
import kurenkov.tutorservice.services.UserDataService;
import kurenkov.tutorservice.services.VisitsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/analyse/api")
public class AnalyseRestController {

    @Autowired
    private VisitsService visitsService;

    @Autowired
    private UserDataService userDataService;

    @Autowired
    private OrderListMapper orderListMapper;

    @Autowired
    private TutorService tutorService;


    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT);


    @GetMapping("/dateanalys")
    public List<Object[]> dateAnalyseTutors() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserData currentUser = userDataService.loadUserDataByUsername(authentication.getName());
        return visitsService.getVisitCountsByDate(currentUser.getTutor().getId());
    }

    @GetMapping("/getuserid")
    public Long getTutorId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserData currentUser = userDataService.loadUserDataByUsername(authentication.getName());
        return currentUser.getId();
    }

    @GetMapping("/getorders")
    public List<OrderDataDTO> getOrders() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserData currentUser = userDataService.loadUserDataByUsername(authentication.getName());
        List<OrderDataDTO> tutorOrdersList = getTutorsData(currentUser);
        return tutorOrdersList;
    }

    @GetMapping("/getprice")
    public Float getPrice() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserData currentUser = userDataService.loadUserDataByUsername(authentication.getName());
        return currentUser.getTutor().getPrice();
    }

    private TutorDataDTO getTutorProfileById(Long id) {
        UserData userData = tutorService.getTutorById(id).getUserData();
        return TutorDataMapper.INSTANCE.userDataToTutorDataDTO(userData);
    }

    private List<OrderDataDTO> getTutorsData(UserData userData){
        List<OrderDataDTO> tutorOrdersList = new ArrayList<>();
        for(Order order: userData.getTutor().getTutorOrders()){
            OrderDataDTO currentOrder = orderListMapper.orderToOrderDataDto(order);
            TutorDataDTO t = getTutorProfileById(currentOrder.getTutor());
            currentOrder.setTutorData(t);
            tutorOrdersList.add(currentOrder);
        }
        return tutorOrdersList;
    }

}
