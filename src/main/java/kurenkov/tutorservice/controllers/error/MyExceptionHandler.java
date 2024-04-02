package kurenkov.tutorservice.controllers.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class MyExceptionHandler {

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(Exception.class)
    public String handler(Exception ex) {

        return ("errors/error404");
    }
}
