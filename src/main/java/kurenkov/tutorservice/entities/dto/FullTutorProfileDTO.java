package kurenkov.tutorservice.entities.dto;

import kurenkov.tutorservice.entities.Address;
import kurenkov.tutorservice.entities.Photo;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class FullTutorProfileDTO {
    private  Long id;
    private String name;
    private String surname;
    private String secName;
    private String specialisation;
    private float price;
    private String description;
    private Address address;
    private Photo photo;
}
