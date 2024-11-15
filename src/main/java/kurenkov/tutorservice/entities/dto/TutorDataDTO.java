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
    protected Long id;
    protected String name;
    protected String surname;
    protected String secName;
    protected String specialisation;
    protected float price;
    protected String description;
    protected Address address;
    protected Photo photo;
    protected String phone;
    protected String email;
}
