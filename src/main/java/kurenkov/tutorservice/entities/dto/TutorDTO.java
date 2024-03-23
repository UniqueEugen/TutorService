package kurenkov.tutorservice.entities.dto;

import jakarta.persistence.*;
import kurenkov.tutorservice.entities.Address;
import kurenkov.tutorservice.entities.Order;
import kurenkov.tutorservice.entities.OrderChat;
import kurenkov.tutorservice.entities.Photo;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TutorDTO {
    private Long id;

    private String specialisation;

    private float price;

    private String description;

    private AddressDTO address;

    private List<Order> tutorOrders=new ArrayList<>();

    private List<OrderChat> chats;

    private PhotoDTO photo;
}
