package kurenkov.tutorservice.services;

import kurenkov.tutorservice.repositories.SeekerRepository;
import kurenkov.tutorservice.entities.Seeker;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeekerService {
    private final SeekerRepository seekerRepository;

    public SeekerService(SeekerRepository seekerRepository) {
        this.seekerRepository = seekerRepository;
    }

    public List<Seeker> getAllSeekers() {
        return seekerRepository.findAll();
    }

    public Seeker getSeekerById(Long id) {
        return seekerRepository.findById(id).orElse(null);
    }

    public Seeker saveSeeker(Seeker seeker) {
        return seekerRepository.save(seeker);
    }

    public void deleteSeeker(Long id) {
        seekerRepository.deleteById(id);
    }
}
