package kurenkov.tutorservice.services;

import kurenkov.tutorservice.entities.PageVisit;
import kurenkov.tutorservice.entities.Tutor;
import kurenkov.tutorservice.repositories.VisitsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VisitsService {

    private VisitsRepository visitsRepository;
    private TutorService tutorService;
    public VisitsService(VisitsRepository visitsRepository, TutorService tutorService) {
        this.visitsRepository = visitsRepository;
        this.tutorService = tutorService;
    }

    public void createVisit(Long tutorId) {
        Tutor tutor = tutorService.getTutorById(tutorId);

        PageVisit pageVisit = new PageVisit();
        pageVisit.setTutor(tutor);
        // Дата будет установлена автоматически в конструкторе PageVisit
        visitsRepository.save(pageVisit);
    }

    public List<PageVisit> getVisitsByTutor(Long tutorId) {
        return visitsRepository.findAll().stream()
                .filter(visit -> visit.getTutor().getId().equals(tutorId))
                .collect(Collectors.toList());
    }

    public List<Object[]> getVisitCountsByDate(Long tutorId) {
        return visitsRepository.findVisitsGroupedByDate(tutorId);
    }

}
