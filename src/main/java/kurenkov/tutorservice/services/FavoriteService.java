package kurenkov.tutorservice.services;


import kurenkov.tutorservice.entities.Tutor;
import kurenkov.tutorservice.entities.UserData;
import kurenkov.tutorservice.repositories.TutorRepository;
import kurenkov.tutorservice.repositories.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class FavoriteService {

    @Autowired
    private UserDataRepository userDataRepository;

    @Autowired
    private TutorRepository tutorRepository; // Репозиторий для получения репетиторов

    @Transactional
    public void addFavoriteTutor(Long userId, Long tutorId) {
        UserData userData = userDataRepository.findByUserId(userId);
        if (userData == null) {
            throw new IllegalArgumentException("User not found");
        }

        Optional<Tutor> tutorOpt = tutorRepository.findById(tutorId);
        if (!tutorOpt.isPresent()) {
            throw new IllegalArgumentException("Tutor not found");
        }

        Tutor tutor = tutorOpt.get();

        // Проверяем, есть ли репетитор в списке любимых
        if (userData.getFavoriteTutors().contains(tutor)) {
            // Если есть, удаляем его
            userData.getFavoriteTutors().remove(tutor);
        } else {
            // Если нет, добавляем его
            userData.getFavoriteTutors().add(tutor);
        }

        userDataRepository.save(userData);
    }
}
