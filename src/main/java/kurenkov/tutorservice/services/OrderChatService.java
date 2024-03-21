package kurenkov.tutorservice.services;

import kurenkov.tutorservice.repositories.OrderChatRepository;
import kurenkov.tutorservice.entities.OrderChat;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderChatService {
    private final OrderChatRepository orderChatRepository;

    public OrderChatService(OrderChatRepository orderChatRepository) {
        this.orderChatRepository = orderChatRepository;
    }

    public List<OrderChat> getAllOrderChats() {
        return orderChatRepository.findAll();
    }

    public OrderChat getOrderChatById(Long id) {
        return orderChatRepository.findById(id).orElse(null);
    }

    public OrderChat saveOrderChat(OrderChat orderChat) {
        return orderChatRepository.save(orderChat);
    }

    public void deleteOrderChat(Long id) {
        orderChatRepository.deleteById(id);
    }
}
