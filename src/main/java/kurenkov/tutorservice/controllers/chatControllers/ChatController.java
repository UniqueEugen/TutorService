package kurenkov.tutorservice.controllers.chatControllers;

import kurenkov.tutorservice.entities.*;
import kurenkov.tutorservice.entities.dto.MessagePayload;
import kurenkov.tutorservice.entities.dto.SeekerDataDTO;
import kurenkov.tutorservice.entities.dto.TutorDataDTO;
import kurenkov.tutorservice.mappers.SeekerMapper;
import kurenkov.tutorservice.mappers.TutorDataMapper;
import kurenkov.tutorservice.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.integration.IntegrationProperties;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.messaging.MessageChannel;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequestMapping("/chat")
public class ChatController {
    private Map<String, IntegrationProperties.Channel> chatChannels = new ConcurrentHashMap<>();

    @Autowired
    private UserDataService userDataService;

    @Autowired
    private TutorService tutorService;

    @Autowired
    private SeekerService seekerService;

    @Autowired
    private OrderChatService orderChatService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private String username;

    @GetMapping
    public String getPage(){
        return "chat/ChatTest";
    }

    @GetMapping("/getChat")
    public String showChat(@RequestParam("id") Long id, Model model){
        setUsername();

        if(!isType()){
            SeekerDataDTO seekerDataDTO = getSeekerProfileById(orderChatService.getOrderChatById(id).getSeeker());
            model.addAttribute("companion", seekerDataDTO);
            model.addAttribute("type", false);
        }else {
            TutorDataDTO tutorDataDTO = getTutorProfileById(orderChatService.getOrderChatById(id).getTutor());
            model.addAttribute("companion", tutorDataDTO);
            model.addAttribute("type", true);
        }
        OrderChat orderChat = orderChatService.getOrderChatById(id);
        model.addAttribute("roomId", orderChat.getId());
        model.addAttribute("chatMessages", orderChat.getChatMessages());
        model.addAttribute("messagePayload", new MessagePayload());
        return "chat/Chat";
    }

    @GetMapping("/createChat")
    public String createChat(@RequestParam("id") Long id, Model model){
        setUsername();
        UserData user = getAccount();
        OrderChat orderChat = new OrderChat(null, null, userDataService.getUserDataById(id).getTutor().getId(), null);
        user.getSeeker().getChats().add(orderChat);
        orderChatService.createOrderChat(orderChat);
        Tutor tutor = tutorService.getTutorById(id);
        tutor.getChats().add(orderChat);
        tutorService.saveTutor(tutor);
        userDataService.saveUserData(user);
        user = getAccount();
        id = user.getSeeker().getChats().get(user.getSeeker().getChats().size()-1).getId();
        return showChat(id, model);
    }




    @MessageMapping("/{roomId}/sendMessage")
    public void sendMessage(@DestinationVariable Long roomId, WebMessage message) {
        // Проверка наличия комнаты в базе данных по roomId
        Optional<OrderChat> chatRoomOptional = Optional.of(orderChatService.getOrderChatById(roomId));
        if (chatRoomOptional.isPresent()) {
            // Комната найдена, обработка сообщения и отправка клиентам в комнате
            OrderChat chatRoom = chatRoomOptional.get();
            ChatMessage chatMessage = new ChatMessage(null, message.getMessage(), Date.valueOf(LocalDate.now()), Time.valueOf(LocalTime.now()), isType(), message.getId());
            chatRoom.getChatMessages().add(chatMessage);
            orderChatService.createChatMessage(chatMessage);
            // Обработка полученного сообщения от клиента
            // Отправка сообщения обратно клиентам в комнате
            messagingTemplate.convertAndSend("/topic/room/" + roomId, message);
        } else {
            // Комната не найдена, обработка ошибки или другая логика
            // ...
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
    private void setUsername(){
        username = SecurityContextHolder.getContext().getAuthentication().getName();
    }
    private boolean isType(){
        UserData user = getAccount();
        if(user.getUser().getRoles().stream().anyMatch(role -> role.getName().equals("TUTOR"))){
            return false;
        }else {
            return true;
        }
    }
}
