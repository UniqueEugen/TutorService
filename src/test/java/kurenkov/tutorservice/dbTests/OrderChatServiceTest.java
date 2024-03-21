package kurenkov.tutorservice.dbTests;

import kurenkov.tutorservice.entities.OrderChat;
import kurenkov.tutorservice.repositories.OrderChatRepository;
import kurenkov.tutorservice.services.OrderChatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.sql.Time;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class OrderChatServiceTest {
    @Mock
    private OrderChatRepository orderChatRepository;

    private OrderChatService orderChatService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        orderChatService = new OrderChatService(orderChatRepository);
    }

    @Test
    void getAllOrderChats_ReturnsListOfChats() {
        // Arrange
        OrderChat chat1 = new OrderChat(1L, "Chat 1", new Date(1234567890L), new Time(9876543210L));
        OrderChat chat2 = new OrderChat(2L, "Chat 2", new Date(9876543210L), new Time(1234567890L));
        List<OrderChat> expectedChats = Arrays.asList(chat1, chat2);

        when(orderChatRepository.findAll()).thenReturn(expectedChats);

        // Act
        List<OrderChat> actualChats = orderChatService.getAllOrderChats();

        // Assert
        assertEquals(expectedChats, actualChats);
    }

    @Test
    void getOrderChatById_ValidId_ReturnsChat() {
        // Arrange
        long chatId = 1L;
        OrderChat expectedChat = new OrderChat(chatId, "Test Chat", new Date(1234567890L), new Time(9876543210L));

        when(orderChatRepository.findById(chatId)).thenReturn(Optional.of(expectedChat));

        // Act
        OrderChat actualChat = orderChatService.getOrderChatById(chatId);

        // Assert
        assertEquals(expectedChat, actualChat);
    }

    @Test
    void getOrderChatById_InvalidId_ReturnsNull() {
        // Arrange
        long chatId = 1L;

        when(orderChatRepository.findById(chatId)).thenReturn(Optional.empty());

        // Act
        OrderChat actualChat = orderChatService.getOrderChatById(chatId);

        // Assert
        assertEquals(null, actualChat);
    }

    @Test
    void saveOrderChat_ReturnsSavedChat() {
        // Arrange
        OrderChat chatToSave = new OrderChat(1L, "New Chat", new Date(1234567890L), new Time(9876543210L));
        OrderChat savedChat = new OrderChat(1L, "Saved Chat", new Date(9876543210L), new Time(1234567890L));

        when(orderChatRepository.save(chatToSave)).thenReturn(savedChat);

        // Act
        OrderChat actualChat = orderChatService.saveOrderChat(chatToSave);

        // Assert
        assertEquals(savedChat, actualChat);
    }

    @Test
    void deleteOrderChat_ValidId_DeletesChat() {
        // Arrange
        long chatId = 1L;

        // Act
        orderChatService.deleteOrderChat(chatId);

        // Assert
        verify(orderChatRepository, times(1)).deleteById(chatId);
    }
}