package kurenkov.tutorservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tutor")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tutor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tutor_id", nullable = false)
    private Long id;

    @Column(name = "specialisation", nullable = false)
    private String specialisation;

    @Column(name = "price", nullable = false)
    private float price;

    @Lob
    @Column(name = "photo", nullable = true)
    private byte[] photo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "tutor_order")
    private List<Order> tutorOrders=new ArrayList<>();

    @OneToMany(cascade = CascadeType.PERSIST)
    private List<OrderChat> chats;
}
