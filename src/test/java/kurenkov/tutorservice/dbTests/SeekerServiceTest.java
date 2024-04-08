package kurenkov.tutorservice.dbTests;

import kurenkov.tutorservice.entities.UserData;
import kurenkov.tutorservice.repositories.SeekerRepository;
import kurenkov.tutorservice.entities.Seeker;
import kurenkov.tutorservice.services.SeekerService;
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
class SeekerServiceTest {
    @Mock
    private SeekerRepository seekerRepo;

    private SeekerService seekerService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        seekerService = new SeekerService(seekerRepo);
    }

    @Test
    void getAllSeekers_ReturnsListOfSeekers() {
        // Arrange
        List<Seeker> expectedSeekers = new ArrayList<>();
        expectedSeekers.add(new Seeker(1L, null, "Description 1", new UserData(), new ArrayList<>(), new ArrayList<>()));
        expectedSeekers.add(new Seeker(2L, null, "Description 2", new UserData(), new ArrayList<>(), new ArrayList<>()));

        when(seekerRepo.findAll()).thenReturn(expectedSeekers);

        // Act
        List<Seeker> actualSeekers = seekerService.getAllSeekers();

        // Assert
        assertEquals(expectedSeekers, actualSeekers);
    }

    @Test
    void getSeekerById_ValidId_ReturnsSeeker() {
        // Arrange
        long seekerId = 1L;
        Seeker expectedSeeker = new Seeker(seekerId, null, "Test Description", new UserData(), new ArrayList<>(), new ArrayList<>());

        when(seekerRepo.findById(seekerId)).thenReturn(Optional.of(expectedSeeker));

        // Act
        Seeker actualSeeker = seekerService.getSeekerById(seekerId);

        // Assert
        assertEquals(expectedSeeker, actualSeeker);
    }

    @Test
    void getSeekerById_InvalidId_ReturnsNull() {
        // Arrange
        long seekerId = 1L;

        when(seekerRepo.findById(seekerId)).thenReturn(Optional.empty());

        // Act
        Seeker actualSeeker = seekerService.getSeekerById(seekerId);

        // Assert
        assertEquals(null, actualSeeker);
    }

    @Test
    void saveSeeker_ReturnsSavedSeeker() {
        // Arrange
        Seeker seekerToSave = new Seeker(1L, null, "New Description", new UserData(), new ArrayList<>(),  new ArrayList<>());
        Seeker savedSeeker = new Seeker(1L, null, "Saved Description", new UserData(), new ArrayList<>(), new ArrayList<>());

        when(seekerRepo.save(seekerToSave)).thenReturn(savedSeeker);

        // Act
        Seeker actualSeeker = seekerService.saveSeeker(seekerToSave);

        // Assert
        assertEquals(savedSeeker, actualSeeker);
    }

    @Test
    void deleteSeeker_ValidId_DeletesSeeker() {
        // Arrange
        long seekerId = 1L;

        // Act
        seekerService.deleteSeeker(seekerId);

        // Assert
        verify(seekerRepo, times(1)).deleteById(seekerId);
    }
}
