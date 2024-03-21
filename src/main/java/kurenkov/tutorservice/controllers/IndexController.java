package kurenkov.tutorservice.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/")
public class IndexController {

    @GetMapping
    public RedirectView redirectToHomePage() {
        return new RedirectView("/home");
    }
}
