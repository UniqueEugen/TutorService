package kurenkov.tutorservice.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "seeker")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seeker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seeker_id", nullable = false)
    private Long id;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "city", nullable = false)
    private String city;

    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "seeker_order")
    private List<Order> seekerOrders=new ArrayList<>();

    @OneToMany(cascade = CascadeType.PERSIST)
    private List<OrderChat> chats=new ArrayList<>();
}
