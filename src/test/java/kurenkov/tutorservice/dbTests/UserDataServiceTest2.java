package kurenkov.tutorservice.dbTests;

import kurenkov.tutorservice.entities.*;
import kurenkov.tutorservice.repositories.RoleRepository;
import kurenkov.tutorservice.repositories.UserDataRepository;
import kurenkov.tutorservice.repositories.UserDataRepository;
import kurenkov.tutorservice.repositories.UserRepository;
import kurenkov.tutorservice.services.TutorService;
import kurenkov.tutorservice.services.UserDataService;
import kurenkov.tutorservice.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Date;
import java.sql.Time;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserDataServiceTest2 {
    @Mock
    private UserDataRepository userDataRepo;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserService userService;

    private UserDataService userDataService;

    private TutorService tutorService;


    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userRepository, passwordEncoder);
        userDataService = new UserDataService(userDataRepo, userService, passwordEncoder, tutorService);
    }

    @Test
    void getAllUserData_ReturnsListOfUserData() {
        // Arrange
        List<UserData> expectedUserDataList = new ArrayList<>();
        Long userId = 1L;
        User user = new User(userId, "testuser", "password", null, null);
        OrderChat chat1 = new OrderChat(1L, new ArrayList<>(), 1L, 2L);
        OrderChat chat2 = new OrderChat(2L, new ArrayList<>(), 1L, 2L);
        List<OrderChat> chats = Arrays.asList(chat1, chat2);
        Order order1 = new Order(1L, new Date(1234567890L), new Time(9876543210L), "Order 1", null, null);
        Order order2 = new Order(2L, new Date(9876543210L), new Time(1234567890L), "Order 2", null, null);
        List<Order> ordersT = Arrays.asList(order1, order2);
        Tutor tutor = new Tutor(1L, "Math", 10.0f, null, null, new Address(1L, "Country", "City", "Street", "1", "Office"), new UserData(), ordersT, chats, null, null, null);
        /*Order order3 = new Order(3L, new Date(9876543220L), new Time(1234567890L), "Order 3");
        List<Order> ordersS = Arrays.asList(order1, order3);
        Seeker seeker = new Seeker(1L, 25, "Description", ordersS, chats);*/
        UserData userData1 = new UserData(1L, "John", "Dou", null, "es4709569@gmail.com",
                "+375293943507",10, tutor, null, null, user);
        UserData userData2 = new UserData(1L, "John", "Dou", null, "es4709569@gmail.com",
                "+375293943507", 10, tutor, null, null, user);
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
        UserData expectedUserData = new UserData(userDataId, "John", "Doe", null, "john@example.com", "1234567890", 10, null, null, null, null);

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
        UserData userDataToSave = new UserData(1L, "John", "Doe", null, "john@example.com", "1234567890", 10, null, null, null,  new User(1L, "pass", "1234", null, new HashSet<Role>()));
        UserData savedUserData = new UserData(1L, "John", "Doe", null,"john@example.com",  "1234567890", 10, null, null, null, new User(1L, "pass", "1234", null, new HashSet<Role>()));

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
