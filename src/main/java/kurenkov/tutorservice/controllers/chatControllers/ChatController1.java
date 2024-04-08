package kurenkov.tutorservice.controllers.chatControllers;

import kurenkov.tutorservice.entities.*;
import kurenkov.tutorservice.services.OrderChatService;
import kurenkov.tutorservice.services.UserDataService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Controller
public class ChatController1 {

    @Autowired
    private OrderChatService orderChatService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private UserDataService userDataService;


    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public TestMessage sendMessage(
            @Payload TestMessage chatMessage
    ) {
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public TestMessage addUser(
            @Payload TestMessage chatMessage,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

   /* @MessageMapping("/chat/{roomId}/createRoom")
    public void createRoom*/
    @MessageMapping("/chat/{roomId}/sendMessage")
    public void sendMessage(@DestinationVariable Long roomId, WebMessage message) {
        String chatMessages = message.getMessage();
        // Проверка наличия комнаты в базе данных по roomId
        Optional<OrderChat> chatRoomOptional = Optional.of(orderChatService.getOrderChatById(roomId));
        if (chatRoomOptional.isPresent()) {
            // Комната найдена, обработка сообщения и отправка клиентам в комнате
            OrderChat chatRoom = chatRoomOptional.get();
            ChatMessage chatMessage = new ChatMessage(null, message.getMessage(), Date.valueOf(LocalDate.now()), Time.valueOf(LocalTime.now()), message.isType(), null);
            chatRoom.getChatMessages().add(chatMessage);
            orderChatService.createChatMessage(chatMessage);
            orderChatService.createOrderChat(chatRoom);
            // Обработка полученного сообщения от клиента
            // Отправка сообщения обратно клиентам в комнате
            messagingTemplate.convertAndSend("/topic/room/" + roomId, message);
        } else {
            // Комната не найдена, обработка ошибки или другая логика
            // ...
        }
    }

    private boolean isType(){
        UserData user = getAccount();
        if(user.getUser().getRoles().stream().anyMatch(role -> role.getName().equals("TUTOR"))){
            return false;
        }else {
            return true;
        }
    }

    private String setUsername(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    private UserData getAccount() {
        return userDataService.loadUserDataByUsername(setUsername());
    }
}