package kurenkov.tutorservice.entities.dto;

import jakarta.persistence.*;
import kurenkov.tutorservice.entities.Order;
import kurenkov.tutorservice.entities.OrderChat;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SeekerDTO {

    private Long id;


    private String description;


    private String city;


    private List<Order> seekerOrders=new ArrayList<>();

    private List<OrderChat> chats=new ArrayList<>();
}
