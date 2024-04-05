package kurenkov.tutorservice.dbTests;

import kurenkov.tutorservice.entities.Order;
import kurenkov.tutorservice.repositories.OrderRepository;
import kurenkov.tutorservice.services.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.sql.Time;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class OrderServiceTest {
    @Mock
    private OrderRepository orderRepo;

    private OrderService orderService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        orderService = new OrderService(orderRepo);
    }

    @Test
    void getAllOrders_ReturnsListOfOrders() {
        // Arrange
        Order order1 = new Order(1L, new Date(1234567890L), new Time(9876543210L), "Order 1", null, null);
        Order order2 = new Order(2L, new Date(9876543210L), new Time(1234567890L), "Order 2", null, null);
        List<Order> expectedOrders = Arrays.asList(order1, order2);

        when(orderRepo.findAll()).thenReturn(expectedOrders);

        // Act
        List<Order> actualOrders = orderService.getAllOrders();

        // Assert
        assertEquals(expectedOrders, actualOrders);
    }

    @Test
    void getOrderById_ValidId_ReturnsOrder() {
        // Arrange
        long orderId = 1L;
        Order expectedOrder = new Order(orderId, new Date(1234567890L), new Time(9876543210L), "Test Order" , null, null);

        when(orderRepo.findById(orderId)).thenReturn(Optional.of(expectedOrder));

        // Act
        Order actualOrder = orderService.getOrderById(orderId);

        // Assert
        assertEquals(expectedOrder, actualOrder);
    }

    @Test
    void getOrderById_InvalidId_ReturnsNull() {
        // Arrange
        long orderId = 1L;

        when(orderRepo.findById(orderId)).thenReturn(Optional.empty());

        // Act
        Order actualOrder = orderService.getOrderById(orderId);

        // Assert
        assertEquals(null, actualOrder);
    }

    @Test
    void saveOrder_ReturnsSavedOrder() {
        // Arrange
        Order orderToSave = new Order(1L, new Date(1234567890L), new Time(9876543210L), "New Order", null , null);
        Order savedOrder = new Order(1L, new Date(9876543210L), new Time(1234567890L), "Saved Order", null , null);

        when(orderRepo.save(orderToSave)).thenReturn(savedOrder);

        // Act
        Order actualOrder = orderService.saveOrder(orderToSave);

        // Assert
        assertEquals(savedOrder, actualOrder);
    }

    @Test
    void deleteOrder_ValidId_DeletesOrder() {
        // Arrange
        long orderId = 1L;

        // Act
        orderService.deleteOrder(orderId);

        // Assert
        verify(orderRepo, times(1)).deleteById(orderId);
    }
}