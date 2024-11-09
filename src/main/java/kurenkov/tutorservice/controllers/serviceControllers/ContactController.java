package kurenkov.tutorservice.controllers.serviceControllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/contact")
public class ContactController {

    @RequestMapping
    public String getContacts() {
        return "service/Contact"; // Возвращаем имя представления для страницы 404
    }

}