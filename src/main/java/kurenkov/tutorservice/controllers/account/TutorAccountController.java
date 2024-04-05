package kurenkov.tutorservice.controllers.account;
import kurenkov.tutorservice.entities.*;
import kurenkov.tutorservice.entities.dto.OrderDataDTO;
import kurenkov.tutorservice.entities.dto.TutorDataDTO;
import kurenkov.tutorservice.mappers.OrderListMapper;
import kurenkov.tutorservice.mappers.TutorDataMapper;
import kurenkov.tutorservice.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private SeekerService seekerService;

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderListMapper orderListMapper;

    private String username;

    // Обработка GET-запроса для отображения страницы личного кабинета
    @GetMapping
    public String showAccountPage(Model model) {
        username = SecurityContextHolder.getContext().getAuthentication().getName();
        // Здесь можно добавить логику для получения данных аккаунта из базы данных или другого источника
        UserData user = getAccount(); // Ваша логика получения аккаунта
        model.addAttribute("account", user);
        List<OrderDataDTO> tutorOrdersList = new ArrayList<>();
        for (Order order: user.getSeeker().getSeekerOrders()){
            OrderDataDTO currentOrder = orderListMapper.orderToOrderDataDto(order);
            TutorDataDTO t = getTutorProfileById(currentOrder.getTutor());
            currentOrder.setTutorData(t);
            tutorOrdersList.add(currentOrder);
        }
        model.addAttribute("seekerOrders", tutorOrdersList);
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
    public ResponseEntity<String> updateUserData(@RequestBody UserData userData) {
        try{
            userDataService.saveUserData(getAccount().updateUserData(userData));// Ваша логика обновления персональных данных
            String responseMessage = "Successfully changed!";
            return ResponseEntity.ok()
                    .header("Content-Type", "text/plain")
                    .body(responseMessage);
        }catch (Exception e) {
            // Возвращаем ошибку с соответствующим кодом состояния
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("Content-Type",
                    "text/plain").body("Change failed!");
        }
        // Здесь можно добавить логику для обновления персональных данных в базе данных или другом хранилище
    }

    // Обработка POST-запроса для обновления данных адреса
    @PostMapping("/setaddress")
    public ResponseEntity<String> updateAddressData(@RequestBody Address address) {
        try{
            addressService.saveAddress(getAccount().getTutor().getAddress().updateAdress(address)); // Ваша логика обновления данных адреса
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

    @PostMapping("/setTutor")
    public ResponseEntity<String> updateTutorData(@RequestBody Tutor tutor){
        try{
        tutorService.saveTutor(getAccount().getTutor().updateTutorData(tutor));
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

    @GetMapping("/changeOrderStatus")
    public String changeOrderStatus(@RequestParam("id") Long orderId, @RequestParam("status") String status) {
        // Получение заказа по идентификатору
        Order order = orderService.getOrderById(orderId);

        if (order != null) {
            // Установка нового статуса для заказа
            order.setStatus(status);
            // Сохранение изменений в базе данных
            orderService.saveOrder(order);
        }

        // Перенаправление пользователя на нужную страницу
        return "redirect:/account/tutor"; // Замените на свой URL
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
    private TutorDataDTO getTutorProfileById(Long id) {
        return TutorDataMapper.INSTANCE.userDataToTutorDataDTO(tutorService.getTutorById(id).getUserData());
    }

}
