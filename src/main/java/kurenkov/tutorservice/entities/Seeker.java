package kurenkov.tutorservice.entities;


import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "seeker")
@Data
public class Seeker {
    @Id
    @GeneratedValue
    @Column(name = "seeker_id", nullable = false)
    private Long id;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "description", nullable = true)
    private String description;

    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "seeker_order")
    private List<Order> seekerOrders=new ArrayList<>();
}
