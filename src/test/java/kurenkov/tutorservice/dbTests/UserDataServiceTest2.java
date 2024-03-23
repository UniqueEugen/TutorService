package kurenkov.tutorservice.dbTests;

import kurenkov.tutorservice.entities.*;
import kurenkov.tutorservice.repositories.UserDataRepository;
import kurenkov.tutorservice.repositories.UserDataRepository;
import kurenkov.tutorservice.repositories.UserRepository;
import kurenkov.tutorservice.services.UserDataService;
import kurenkov.tutorservice.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserDataServiceTest2 {
    @Mock
    private UserDataRepository userDataRepo;

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    private UserDataService userDataService;


    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userRepository);
        userDataService = new UserDataService(userDataRepo, userService);
    }

    @Test
    void getAllUserData_ReturnsListOfUserData() {
        // Arrange
        List<UserData> expectedUserDataList = new ArrayList<>();
        Long userId = 1L;
        User user = new User(userId, "testuser", "password");
        OrderChat chat1 = new OrderChat(1L, "Chat 1", new Date(1234567890L), new Time(9876543210L));
        OrderChat chat2 = new OrderChat(2L, "Chat 2", new Date(9876543210L), new Time(1234567890L));
        List<OrderChat> chats = Arrays.asList(chat1, chat2);
        Order order1 = new Order(1L, new Date(1234567890L), new Time(9876543210L), "Order 1");
        Order order2 = new Order(2L, new Date(9876543210L), new Time(1234567890L), "Order 2");
        List<Order> ordersT = Arrays.asList(order1, order2);
        Tutor tutor = new Tutor(1L, "Math", 10.0f, null, new Address(1L, "Country", "City", "Street", "1", "Office"), ordersT, chats, null);
        /*Order order3 = new Order(3L, new Date(9876543220L), new Time(1234567890L), "Order 3");
        List<Order> ordersS = Arrays.asList(order1, order3);
        Seeker seeker = new Seeker(1L, 25, "Description", ordersS, chats);*/
        UserData userData1 = new UserData(1L, "John", "Dou", null, "es4709569@gmail.com",
                "+375293943507",10, tutor, null, user );
        UserData userData2 = new UserData(1L, "John", "Dou", null, "es4709569@gmail.com",
                "+375293943507", 10, tutor, null, user);
        expectedUserDataList.add(userData1);
        expectedUserDataList.add(userData2);

        when(userDataRepo.findAll()).thenReturn(expectedUserDataList);
        when(userDataRepo.findById(userId)).thenReturn(Optional.of(userData1));

        // Вызов метода, который будет тестироваться
        UserData result = userDataService.getUserDataById(userId);

        // Проверка ожидаемого результата
        assertEquals(userData1, result);

        // Act
        List<UserData> actualUserDataList = userDataService.getAllUserData();

        // Assert
        assertEquals(expectedUserDataList, actualUserDataList);
    }

    @Test
    void getUserDataById_ValidId_ReturnsUserData() {
        // Arrange
        long userDataId = 1L;
        UserData expectedUserData = new UserData(userDataId, "John", "Doe", null, "john@example.com", "1234567890", 10, null, null, null);

        when(userDataRepo.findById(userDataId)).thenReturn(Optional.of(expectedUserData));

        // Act
        UserData actualUserData = userDataService.getUserDataById(userDataId);

        // Assert
        assertEquals(expectedUserData, actualUserData);
    }

    @Test
    void getUserDataById_InvalidId_ReturnsNull() {
        // Arrange
        long userDataId = 1L;

        when(userDataRepo.findById(userDataId)).thenReturn(Optional.empty());

        // Act
        UserData actualUserData = userDataService.getUserDataById(userDataId);

        // Assert
        assertEquals(null, actualUserData);
    }

    @Test
    void saveUserData_ReturnsSavedUserData() {
        // Arrange
        UserData userDataToSave = new UserData(1L, "John", "Doe", null, "john@example.com", "1234567890", 10, null, null, null);
        UserData savedUserData = new UserData(1L, "John", "Doe", null,"john@example.com",  "1234567890", 10, null, null, null);

        when(userDataRepo.save(userDataToSave)).thenReturn(savedUserData);

        // Act
        UserData actualUserData = userDataService.saveUserData(userDataToSave);

        // Assert
        assertEquals(savedUserData, actualUserData);
    }

    @Test
    void deleteUserData_ValidId_DeletesUserData() {
        // Arrange
        long userDataId = 1L;

        // Act
        userDataService.deleteUserData(userDataId);

        // Assert
        verify(userDataRepo, times(1)).deleteById(userDataId);
    }
}
