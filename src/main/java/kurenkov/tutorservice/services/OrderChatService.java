package kurenkov.tutorservice.services;

import kurenkov.tutorservice.entities.ChatMessage;
import kurenkov.tutorservice.entities.Order;
import kurenkov.tutorservice.repositories.ChatMessageRepository;
import kurenkov.tutorservice.repositories.OrderChatRepository;
import kurenkov.tutorservice.entities.OrderChat;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderChatService {
    private final OrderChatRepository orderChatRepository;
    private final ChatMessageRepository chatMessageRepository;

    public OrderChatService(OrderChatRepository orderChatRepository, ChatMessageRepository chatMessageRepository) {
        this.orderChatRepository = orderChatRepository;
        this.chatMessageRepository = chatMessageRepository;
    }

    public List<OrderChat> getAllOrderChats() {
        return orderChatRepository.findAll();
    }

    public OrderChat createOrderChat(OrderChat orderChat) {
        return orderChatRepository.save(orderChat);
    }

    public OrderChat getOrderChatById(Long id) {
        return orderChatRepository.findById(id).orElse(null);
    }

    public ChatMessage createChatMessage(ChatMessage chatMessage) {
        return chatMessageRepository.save(chatMessage);
    }

    public void deleteOrderChat(Long id) {
        orderChatRepository.deleteById(id);
    }
}
