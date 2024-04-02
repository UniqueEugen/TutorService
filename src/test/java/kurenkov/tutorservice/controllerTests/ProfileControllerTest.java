package kurenkov.tutorservice.controllerTests;

import kurenkov.tutorservice.controllers.profile.ProfileController;
import kurenkov.tutorservice.entities.Seeker;
import kurenkov.tutorservice.entities.Statuses;
import kurenkov.tutorservice.entities.Tutor;
import kurenkov.tutorservice.entities.UserData;
import kurenkov.tutorservice.services.UserDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ProfileControllerTest {

    @Mock
    private UserDataService userDataService;

    @InjectMocks
    private ProfileController orderController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testCreateOrder_Exception() {
        // Arrange
        ProfileController.OrderData orderData = new ProfileController.OrderData(5L, Statuses.PENDING, "18-12-2003", "18:55");

        when(userDataService.loadUserDataByUsername(anyString())).thenThrow(new RuntimeException());

        // Act
        ResponseEntity<String> response = orderController.createOrder(orderData);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Registration failed.", response.getBody());
        verify(userDataService, never()).saveUserData(any(UserData.class));
    }
}
