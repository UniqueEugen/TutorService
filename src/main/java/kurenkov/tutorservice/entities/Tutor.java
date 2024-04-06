package kurenkov.tutorservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tutor")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tutor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tutor_id", nullable = false)
    private Long id;

    @Column(name = "specialisation", nullable = false)
    private String specialisation;

    @Column(name = "price", nullable = false)
    private float price;

    @Column(name = "description")
    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "tutor")
    private UserData userData;

    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "tutor_order")
    private List<Order> tutorOrders=new ArrayList<>();

    @OneToMany(cascade = CascadeType.PERSIST)
    private List<OrderChat> chats;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "photo_id", referencedColumnName = "id", nullable = true)
    private Photo photo;


    public Tutor updateTutorData(Tutor newTutorData) {
        Field[] fields = Tutor.class.getDeclaredFields();

        for (Field field : fields) {
            String fieldName = field.getName();
            if (!fieldName.equals("address") && !fieldName.equals("photo") &&  !fieldName.equals("id")
                    &&  !fieldName.equals("tutorOrders") &&  !fieldName.equals("chats")) {
                field.setAccessible(true); // Разрешаем доступ к приватным полям
                try {
                    Object value = field.get(newTutorData);
                    field.set(this, value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return this;
    }
}
