package kurenkov.tutorservice.dbTests;

import kurenkov.tutorservice.entities.*;
import kurenkov.tutorservice.repositories.SeekerRepository;
import kurenkov.tutorservice.repositories.TutorRepository;
import kurenkov.tutorservice.repositories.UserDataRepository;
import kurenkov.tutorservice.repositories.UserRepository;
import kurenkov.tutorservice.services.UserDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
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


@RunWith(MockitoJUnitRunner.class)
class UserDataServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private TutorRepository tutorRepository;

    @Mock
    private SeekerRepository seekerRepository;

    @Mock
    private UserDataRepository userDataRepository;

    @InjectMocks
    private UserDataService userDataService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetUserDataByIdTutor() {
        // Создание тестовых данных
        Long userId = 1L;
        User user = new User(userId, "testuser", "password");
        OrderChat chat1 = new OrderChat(1L, "Chat 1", new Date(1234567890L), new Time(9876543210L));
        OrderChat chat2 = new OrderChat(2L, "Chat 2", new Date(9876543210L), new Time(1234567890L));
        List<OrderChat> chats = Arrays.asList(chat1, chat2);
        Order order1 = new Order(1L, new Date(1234567890L), new Time(9876543210L), "Order 1");
        Order order2 = new Order(2L, new Date(9876543210L), new Time(1234567890L), "Order 2");
        List<Order> ordersT = Arrays.asList(order1, order2);
        Tutor tutor = new Tutor(1L, "Math", 10.0f, null, new Address(1L, "Country", "City","Street", "1", "Office"), ordersT, chats);
        UserData expectedUserData = new UserData(userId, "John", "Dou", null, "es4709569@gmail.com",
                "+375293943507", tutor, null, user);

        // Настройка мок-объектов
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(tutorRepository.findById(userId)).thenReturn(Optional.of(tutor));
        when(userDataRepository.findById(userId)).thenReturn(Optional.of(expectedUserData));

        // Вызов метода, который будет тестироваться
        UserData result = userDataService.getUserDataById(userId);

        // Проверка ожидаемого результата
        assertEquals(expectedUserData, result);

        // Проверка вызова методов репозиториев
        verify(userRepository, times(1)).findById(userId);
        verify(tutorRepository, times(1)).findById(userId);
        verify(seekerRepository, times(0)).findById(userId);
    }

    @Test
    void testGetUserDataByIdSeeker() {
        // Создание тестовых данных
        Long userId = 1L;
        User user = new User(userId, "testuser", "password");
        OrderChat chat1 = new OrderChat(1L, "Chat 1", new Date(1234567890L), new Time(9876543210L));
        OrderChat chat2 = new OrderChat(2L, "Chat 2", new Date(9876543210L), new Time(1234567890L));
        List<OrderChat> chats = Arrays.asList(chat1, chat2);
        Order order1 = new Order(1L, new Date(1234567890L), new Time(9876543210L), "Order 1");
        Order order3 = new Order(3L, new Date(9876543220L), new Time(1234567890L), "Order 3");
        List<Order> ordersS = Arrays.asList(order1, order3);
        Seeker seeker = new Seeker(1L, 25, "Description", ordersS, chats);
        UserData expectedUserData = new UserData(userId, "John", "Dou", null, "es4709569@gmail.com",
                "+375293943507", null, seeker, user);

        // Настройка мок-объектов
        when(userRepository.findById
                (userId)).thenReturn(Optional.of(user));
        when(tutorRepository.findById(userId)).thenReturn(Optional.empty());
        when(seekerRepository.findById(userId)).thenReturn(Optional.of(seeker));
        when(userDataRepository.findById(userId)).thenReturn(Optional.of(expectedUserData));

        // Вызов метода, который будет тестироваться
        UserData result = userDataService.getUserDataById(userId);

        // Проверка ожидаемого результата
        assertEquals(expectedUserData, result);

        // Проверка вызова методов репозиториев
        verify(userRepository, times(1)).findById(userId);
        verify(tutorRepository, times(1)).findById(userId);
        verify(seekerRepository, times(1)).findById(userId);
    }
}