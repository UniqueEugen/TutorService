package kurenkov.tutorservice.dbTests;

import static org.mockito.Mockito.*;

import kurenkov.tutorservice.repositories.TutorViewsRepository;
import kurenkov.tutorservice.services.TutorViewsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TutorViewsServiceTest {

    @Mock
    private TutorViewsRepository tutorViewsRepository;

    @InjectMocks
    private TutorViewsService tutorViewsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddOrUpdateTutorView_WhenRecordExists_DeletesAndInserts() {
        Long userId = 1L;
        Long tutorId = 2L;

        // Настройка поведения мока
        when(tutorViewsRepository.existsByUserIdAndTutorId(userId, tutorId)).thenReturn(true);

        // Вызов метода
        tutorViewsService.addOrUpdateTutorView(userId, tutorId);

        // Проверка, что удаление и вставка были вызваны
        verify(tutorViewsRepository).deleteByUserIdAndTutorId(userId, tutorId);
        verify(tutorViewsRepository).insertTutorView(userId, tutorId);
    }

    @Test
    public void testAddOrUpdateTutorView_WhenRecordDoesNotExist_Inserts() {
        Long userId = 1L;
        Long tutorId = 2L;

        // Настройка поведения мока
        when(tutorViewsRepository.existsByUserIdAndTutorId(userId, tutorId)).thenReturn(false);

        // Вызов метода
        tutorViewsService.addOrUpdateTutorView(userId, tutorId);

        // Проверка, что вставка была вызвана, а удаление — нет
        verify(tutorViewsRepository, never()).deleteByUserIdAndTutorId(userId, tutorId);
        verify(tutorViewsRepository).insertTutorView(userId, tutorId);
    }
}
