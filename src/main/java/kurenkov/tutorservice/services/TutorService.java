package kurenkov.tutorservice.services;

import kurenkov.tutorservice.entities.dto.TutorDataDTO;
import kurenkov.tutorservice.repositories.TutorRepository;
import kurenkov.tutorservice.entities.Tutor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TutorService {
    private final TutorRepository tutorRepository;

    public TutorService(TutorRepository tutorRepository) {
        this.tutorRepository = tutorRepository;
    }

    public List<Tutor> getAllTutors() {
        return tutorRepository.findAll();
    }

    public Tutor getTutorById(Long id) {
        return tutorRepository.findById(id).orElse(null);
    }

    public Tutor saveTutor(Tutor tutor) {
        return tutorRepository.save(tutor);
    }

    public void deleteTutor(Long id) {
        tutorRepository.deleteById(id);
    }
}
