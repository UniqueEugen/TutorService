package kurenkov.tutorservice.controllers.serviceControllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/about")
public class AboutUsController {

    @RequestMapping
    public String getContacts() {
        return "service/AboutUs"; // Возвращаем имя представления для страницы 404
    }

}
