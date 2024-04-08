package kurenkov.tutorservice.entities;


import jakarta.persistence.*;
import lombok.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "seeker")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Seeker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seeker_id", nullable = false)
    private Long id;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "city", nullable = false)
    private String city;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "seeker")
    private UserData userData;

    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "seeker_order")
    private List<Order> seekerOrders=new ArrayList<>();

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "seeker_id")
    private List<OrderChat> chats=new ArrayList<>();



    public Seeker updateSeekerData(Seeker newSeekerData) {
        Field[] fields = Seeker.class.getDeclaredFields();

        for (Field field : fields) {
            String fieldName = field.getName();
            if (!fieldName.equals("id")
                    &&  !fieldName.equals("seekerOrders")
                    &&  !fieldName.equals("chats")) {
                field.setAccessible(true); // Разрешаем доступ к приватным полям
                try {
                    Object value = field.get(newSeekerData);
                    field.set(this, value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return this;
    }
}
