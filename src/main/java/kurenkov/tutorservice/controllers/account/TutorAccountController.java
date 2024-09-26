package kurenkov.tutorservice.controllers.account;
import kurenkov.tutorservice.entities.*;
import kurenkov.tutorservice.entities.dto.ChatDTO;
import kurenkov.tutorservice.entities.dto.OrderDataDTO;
import kurenkov.tutorservice.entities.dto.SeekerDataDTO;
import kurenkov.tutorservice.entities.dto.TutorDataDTO;
import kurenkov.tutorservice.mappers.ChatListMapper;
import kurenkov.tutorservice.mappers.OrderListMapper;
import kurenkov.tutorservice.mappers.SeekerMapper;
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
@RequestMapping("/account")
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

    @Autowired
    private ChatListMapper chatListMapper;

    private String username;

    private void setUsername(){
        username = SecurityContextHolder.getContext().getAuthentication().getName();
    }

    // Обработка GET-запроса для отображения страницы личного кабинета
    @GetMapping("/seeker")
    public String showAccountPage(Model model) {
        setUsername();
        // Здесь можно добавить логику для получения данных аккаунта из базы данных или другого источника
        UserData user = getAccount(); // Ваша логика получения аккаунта
        model.addAttribute("account", user);
        List<OrderDataDTO> seekerOrdersList = getSeekersData(user);
        List<ChatDTO> seekerChatDTO = getSeekerChatData(user);
        model.addAttribute("seekerOrders", seekerOrdersList);
        model.addAttribute("seekerChats", seekerChatDTO);

        return "account/tutorAccountPage"; // Возвращаем имя шаблона Thymeleaf
    }

    @GetMapping("/tutor")
    public String showSeekerPage(Model model){
        setUsername();
        UserData userData = getAccount();
        List<OrderDataDTO> seekerOrdersList = getSeekersData(userData);
        List<OrderDataDTO> tutorOrdersList = getTutorsData(userData);
        List<ChatDTO> seekerChatDTO = getSeekerChatData(userData);
        List<ChatDTO> tutorChatDTO = getTutorChatData(userData);
        model.addAttribute("seekerOrders", seekerOrdersList);
        model.addAttribute("tutorOrders", tutorOrdersList);
        model.addAttribute("seekerChats", seekerChatDTO);
        model.addAttribute("tutorChats", tutorChatDTO);
        return showAccountPage(model);
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
    @PostMapping("/setAddress")
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

    @PostMapping("/setSeeker")
    public ResponseEntity<String> updateSeekerData(@RequestBody Seeker seeker){
        try{
            seekerService.saveSeeker(getAccount().getSeeker().updateSeekerData(seeker));
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
        return "redirect:/getcurrentpage"; // Замените на свой URL
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
        UserData userData = tutorService.getTutorById(id).getUserData();
        return TutorDataMapper.INSTANCE.userDataToTutorDataDTO(userData);
    }

    private SeekerDataDTO getSeekerProfileById(Long id) {
        return SeekerMapper.INSTANCE.userDataToSeekerDataDTO(seekerService.getSeekerById(id).getUserData());
    }
    private List<OrderDataDTO> getSeekersData(UserData user){
        List<OrderDataDTO> seekerOrdersList = new ArrayList<>();
        for (Order order: user.getSeeker().getSeekerOrders()){
            OrderDataDTO currentOrder = orderListMapper.orderToOrderDataDto(order);
            TutorDataDTO t = getTutorProfileById(currentOrder.getTutor());
            currentOrder.setTutorData(t);
            seekerOrdersList.add(currentOrder);
        }
        return seekerOrdersList;
    }

    private List<OrderDataDTO> getTutorsData(UserData userData){
        List<OrderDataDTO> tutorOrdersList = new ArrayList<>();
        for(Order order: userData.getTutor().getTutorOrders()){
            OrderDataDTO currentOrder = orderListMapper.orderToOrderDataDto(order);
            SeekerDataDTO s = getSeekerProfileById(currentOrder.getSeeker());
            currentOrder.setSeekerData(s);
            tutorOrdersList.add(currentOrder);
        }
        return tutorOrdersList;
    }

    private List<ChatDTO> getTutorChatData(UserData userData){
        List<ChatDTO> tutorOrdersList = new ArrayList<>();
        for(OrderChat orderChat: userData.getTutor().getChats()){
            ChatDTO chatDTO = chatListMapper.chatToChatDto(orderChat);
            SeekerDataDTO s = getSeekerProfileById(chatDTO.getSeeker());
            chatDTO.setSeekerData(s);
            tutorOrdersList.add(chatDTO);
        }
        return tutorOrdersList;
    }
    private List<ChatDTO> getSeekerChatData(UserData userData){
        List<ChatDTO> tutorOrdersList = new ArrayList<>();
        for(OrderChat orderChat: userData.getSeeker().getChats()){
            ChatDTO chatDTO = chatListMapper.chatToChatDto(orderChat);
            TutorDataDTO s = getTutorProfileById(chatDTO.getTutor());
            chatDTO.setTutorData(s);
            tutorOrdersList.add(chatDTO);
        }
        return tutorOrdersList;
    }


}
