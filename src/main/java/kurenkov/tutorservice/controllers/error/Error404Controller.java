package kurenkov.tutorservice.controllers.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/404")
public class Error404Controller {

    @RequestMapping
    public String error404() {
        return "errors/error404"; // Возвращаем имя представления для страницы 404
    }

}
