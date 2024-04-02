package kurenkov.tutorservice.config.headers;

import org.springframework.security.web.header.HeaderWriter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomHeaderWriter implements HeaderWriter {

    @Override
    public void writeHeaders(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("X-Frame-Options", "SAMEORIGIN");
    }
}
