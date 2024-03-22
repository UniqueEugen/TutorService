package kurenkov.tutorservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_data")
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User user;
}
