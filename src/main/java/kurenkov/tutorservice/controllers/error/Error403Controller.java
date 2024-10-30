package kurenkov.tutorservice.controllers.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/403")
public class Error403Controller {
    @RequestMapping
    public String error404() {
        return "errors/error403";
    }
}
