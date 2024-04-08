package kurenkov.tutorservice.dbTests;

import kurenkov.tutorservice.entities.ChatMessage;
import kurenkov.tutorservice.entities.OrderChat;
import kurenkov.tutorservice.repositories.ChatMessageRepository;
import kurenkov.tutorservice.repositories.OrderChatRepository;
import kurenkov.tutorservice.services.OrderChatService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

public class OrderChatServiceTest {

    @Mock
    private OrderChatRepository orderChatRepository;

    @Mock
    private ChatMessageRepository chatMessageRepository;

    private OrderChatService orderChatService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        orderChatService = new OrderChatService(orderChatRepository, chatMessageRepository);
    }

    @Test
    public void testGetAllOrderChats() {
        // Arrange
        List<OrderChat> expectedOrderChats = new ArrayList<>();
        expectedOrderChats.add(new OrderChat());
        expectedOrderChats.add(new OrderChat());
        Mockito.when(orderChatRepository.findAll()).thenReturn(expectedOrderChats);

        // Act
        List<OrderChat> actualOrderChats = orderChatService.getAllOrderChats();

        // Assert
        Assertions.assertEquals(expectedOrderChats, actualOrderChats);
        Mockito.verify(orderChatRepository).findAll();
    }

    @Test
    public void testCreateOrderChat() {
        // Arrange
        OrderChat orderChat = new OrderChat();
        Mockito.when(orderChatRepository.save(any(OrderChat.class))).thenReturn(orderChat);

        // Act
        OrderChat createdOrderChat = orderChatService.createOrderChat(orderChat);

        // Assert
        Assertions.assertEquals(orderChat, createdOrderChat);
        Mockito.verify(orderChatRepository).save(orderChat);
    }

    @Test
    public void testGetOrderChatById() {
        // Arrange
        Long orderId = 1L;
        OrderChat expectedOrderChat = new OrderChat();
        Mockito.when(orderChatRepository.findById(orderId)).thenReturn(Optional.of(expectedOrderChat));

        // Act
        OrderChat actualOrderChat = orderChatService.getOrderChatById(orderId);

        // Assert
        Assertions.assertEquals(expectedOrderChat, actualOrderChat);
        Mockito.verify(orderChatRepository).findById(orderId);
    }

    @Test
    public void testCreateChatMessage() {
        // Arrange
        ChatMessage chatMessage = new ChatMessage();
        Mockito.when(chatMessageRepository.save(any(ChatMessage.class))).thenReturn(chatMessage);

        // Act
        ChatMessage createdChatMessage = orderChatService.createChatMessage(chatMessage);

        // Assert
        Assertions.assertEquals(chatMessage, createdChatMessage);
        Mockito.verify(chatMessageRepository).save(chatMessage);
    }

    @Test
    public void testDeleteOrderChat() {
        // Arrange
        Long orderId = 1L;

        // Act
        orderChatService.deleteOrderChat(orderId);

        // Assert
        Mockito.verify(orderChatRepository).deleteById(orderId);
    }
}