package kurenkov.tutorservice.entities.dto;

import kurenkov.tutorservice.entities.Address;
import kurenkov.tutorservice.entities.Order;
import kurenkov.tutorservice.entities.OrderChat;
import kurenkov.tutorservice.entities.Photo;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TutorDataDTO {
    private Long id;
    private String name;
    private String surname;
    private String secName;
    private String specialisation;
    private float price;
    private String description;
    private Address address;
    private Photo photo;
}
