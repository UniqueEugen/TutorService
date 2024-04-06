package kurenkov.tutorservice.entities.dto;

import jakarta.persistence.Column;
import lombok.Data;


@Data
public class SeekerDataDTO {
    private Long id;
    private String name;
    private String surname;
    private String secName;
    private String description;
    private String city;
    private int age;
}
