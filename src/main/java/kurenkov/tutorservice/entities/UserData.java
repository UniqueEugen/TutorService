package kurenkov.tutorservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.lang.reflect.Field;
import java.util.List;

@Entity
@Table(name = "user_data")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserData {

    @Id
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "surname", nullable = false)
    private String surname;
    @Column(name = "second_name", nullable = true)
    private String secName;
    @Column(name = "email", nullable = false)
    private String eMail;
    @Column(name = "phone", nullable = false)
    private String phone;
    @Column(name = "age", nullable = false)
    private int age;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tutor_id", referencedColumnName = "tutor_id")
    private Tutor tutor;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "seeker_id", referencedColumnName = "seeker_id")
    private Seeker seeker;

    // Связь с репетиторами
    @ManyToMany
    @JoinTable(
            name = "user_favorite_tutors",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "tutor_id")
    )
    private List<Tutor> favoriteTutors;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User user;


    public UserData updateUserData(UserData userData) {
        Field[] fields = UserData.class.getDeclaredFields();

        for (Field field : fields) {
            String fieldName = field.getName();
            if (!fieldName.equals("tutor") && !fieldName.equals("seeker") && !fieldName.equals("user") && !fieldName.equals("id")) {
                field.setAccessible(true); // Разрешаем доступ к приватным полям
                try {
                    Object value = field.get(userData);
                    field.set(this, value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return this;
    }
}
