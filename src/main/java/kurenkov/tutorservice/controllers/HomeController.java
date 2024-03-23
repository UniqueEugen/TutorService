package kurenkov.tutorservice.controllers;

import kurenkov.tutorservice.entities.Tutor;
import kurenkov.tutorservice.services.TutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private TutorService tutorService;

    @GetMapping
    public String getTutors(Model model) {
        List<Tutor> tutors = tutorService.getAllTutors();
        System.out.println(tutors);
        model.addAttribute("tutors", tutors);
        return "homePage";
    }
}
