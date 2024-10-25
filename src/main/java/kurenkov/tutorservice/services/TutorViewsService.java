package kurenkov.tutorservice.services;

import kurenkov.tutorservice.repositories.TutorViewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TutorViewsService {


    private TutorViewsRepository tutorViewsRepository;

    private TutorViewsService(TutorViewsRepository tutorViewsRepository){
        this.tutorViewsRepository = tutorViewsRepository;

    }

    public void addOrUpdateTutorView(Long userId, Long tutorId) {
        if (tutorViewsRepository.existsByUserIdAndTutorId(userId, tutorId)) {
            // Если запись существует, удаляем ее
            tutorViewsRepository.deleteByUserIdAndTutorId(userId, tutorId);
        }

        // Вставляем новую запись
        tutorViewsRepository.insertTutorView(userId, tutorId);
    }
}
