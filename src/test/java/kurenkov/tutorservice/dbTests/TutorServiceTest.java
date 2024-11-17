package kurenkov.tutorservice.dbTests;

import kurenkov.tutorservice.entities.Address;
import kurenkov.tutorservice.entities.UserData;
import kurenkov.tutorservice.repositories.TutorRepository;
import kurenkov.tutorservice.entities.Tutor;
import kurenkov.tutorservice.services.TutorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class TutorServiceTest {
    @Mock
    private TutorRepository tutorRepo;

    private TutorService tutorService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        tutorService = new TutorService(tutorRepo);
    }

    @Test
    void getAllTutors_ReturnsListOfTutors() {
        // Arrange
        List<Tutor> expectedTutors = new ArrayList<>();
        expectedTutors.add(new Tutor(1L, "Math", 50.0f, null, null, new Address(1L, "Country 1", "City 1", "Street 1", "House 1", "Office 1"), new UserData(), new ArrayList<>(), new ArrayList<>(),null, null, null));
        expectedTutors.add(new Tutor(2L, "Science", 60.0f, null, null, new Address(), new UserData(), new ArrayList<>(), new ArrayList<>(), null, null, null));

        when(tutorRepo.findAll()).thenReturn(expectedTutors);

        // Act
        List<Tutor> actualTutors = tutorService.getAllTutors();

        // Assert
        assertEquals(expectedTutors, actualTutors);
    }

    @Test
    void getTutorById_ValidId_ReturnsTutor() {
        // Arrange
        long tutorId = 1L;
        Tutor expectedTutor = new Tutor(tutorId, "Math", 50.0f,null, null, new Address(1L, "Country 1", "City 1", "Street 1", "House 1", "Office 1"), new UserData(), new ArrayList<>(), new ArrayList<>(), null, null, null);

        when(tutorRepo.findById(tutorId)).thenReturn(Optional.of(expectedTutor));

        // Act
        Tutor actualTutor = tutorService.getTutorById(tutorId);

        // Assert
        assertEquals(expectedTutor, actualTutor);
    }

    @Test
    void getTutorById_InvalidId_ReturnsNull() {
        // Arrange
        long tutorId = 1L;

        when(tutorRepo.findById(tutorId)).thenReturn(Optional.empty());

        // Act
        Tutor actualTutor = tutorService.getTutorById(tutorId);

        // Assert
        assertEquals(null, actualTutor);
    }

    @Test
    void saveTutor_ReturnsSavedTutor() {
        // Arrange
        Tutor tutorToSave = new Tutor(1L, "Math", 50.0f, null, null, new Address(1L, "Country 1", "City 1", "Street 1", "House 1", "Office 1"), new UserData(), new ArrayList<>(), new ArrayList<>(), null, null, null);
        Tutor savedTutor = new Tutor(1L, "Math", 50.0f, null, null, new Address(1L, "Country 1", "City 1", "Street 1", "House 1", "Office 1"), new UserData(), new ArrayList<>(), new ArrayList<>(), null, null, null);

        when(tutorRepo.save(tutorToSave)).thenReturn(savedTutor);

        // Act
        Tutor actualTutor = tutorService.saveTutor(tutorToSave);

        // Assert
        assertEquals(savedTutor, actualTutor);
    }

    @Test
    void deleteTutor_ValidId_DeletesTutor() {
        // Arrange
        long tutorId = 1L;

        // Act
        tutorService.deleteTutor(tutorId);

        // Assert
        verify(tutorRepo, times(1)).deleteById(tutorId);
    }
}
