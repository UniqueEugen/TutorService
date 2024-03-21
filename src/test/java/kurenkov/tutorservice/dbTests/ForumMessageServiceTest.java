package kurenkov.tutorservice.dbTests;

import kurenkov.tutorservice.entities.ForumMessage;
import kurenkov.tutorservice.repositories.ForumMessageRepository;
import kurenkov.tutorservice.services.ForumMessageService;
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
class ForumMessageServiceTest {
    @Mock
    private ForumMessageRepository forumMessageRepository;

    private ForumMessageService forumMessageService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        forumMessageService = new ForumMessageService(forumMessageRepository);
    }

    @Test
    void getAllForumMessages_ReturnsListOfMessages() {
        // Arrange
        ForumMessage message1 = new ForumMessage(1L, "Message 1", new Date(1234567890L), new Time(9876543210L), null);
        ForumMessage message2 = new ForumMessage(2L, "Message 2", new Date(9876543210L), new Time(1234567890L), null);
        List<ForumMessage> expectedMessages = Arrays.asList(message1, message2);

        when(forumMessageRepository.findAll()).thenReturn(expectedMessages);

        // Act
        List<ForumMessage> actualMessages = forumMessageService.getAllForumMessages();

        // Assert
        assertEquals(expectedMessages, actualMessages);
    }

    @Test
    void getForumMessageById_ValidId_ReturnsMessage() {
        // Arrange
        long messageId = 1L;
        ForumMessage expectedMessage = new ForumMessage(messageId, "Test Message", new Date(1234567890L), new Time(9876543210L), null);

        when(forumMessageRepository.findById(messageId)).thenReturn(Optional.of(expectedMessage));

        // Act
        ForumMessage actualMessage = forumMessageService.getForumMessageById(messageId);

        // Assert
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void getForumMessageById_InvalidId_ReturnsNull() {
        // Arrange
        long messageId = 1L;

        when(forumMessageRepository.findById(messageId)).thenReturn(Optional.empty());

        // Act
        ForumMessage actualMessage = forumMessageService.getForumMessageById(messageId);

        // Assert
        assertEquals(null, actualMessage);
    }

    @Test
    void saveForumMessage_ReturnsSavedMessage() {
        // Arrange
        ForumMessage messageToSave = new ForumMessage(1L, "New Message", new Date(1234567890L), new Time(9876543210L), null);
        ForumMessage savedMessage = new ForumMessage(1L, "Saved Message", new Date(9876543210L), new Time(1234567890L), null);

        when(forumMessageRepository.save(messageToSave)).thenReturn(savedMessage);

        // Act
        ForumMessage actualMessage = forumMessageService.saveForumMessage(messageToSave);

        // Assert
        assertEquals(savedMessage, actualMessage);
    }

    @Test
    void deleteForumMessage_ValidId_DeletesMessage() {
        // Arrange
        long messageId = 1L;

        // Act
        forumMessageService.deleteForumMessage(messageId);

        // Assert
        verify(forumMessageRepository, times(1)).deleteById(messageId);
    }
}