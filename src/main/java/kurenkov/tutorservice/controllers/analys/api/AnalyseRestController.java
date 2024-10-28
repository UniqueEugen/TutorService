package kurenkov.tutorservice.controllers.analys.api;

import kurenkov.tutorservice.services.VisitsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/analyse/api")
public class AnalyseRestController {

    @Autowired
    private VisitsService visitsService;


    @GetMapping("/dateanalys")
    public List<Object[]> dateAnalyseTutors() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return visitsService.getVisitCountsByDate(25L);
    }

}
