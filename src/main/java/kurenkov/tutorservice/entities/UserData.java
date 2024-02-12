package kurenkov.tutorservice.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user_data")
@Data
public class UserData {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tutor_id", referencedColumnName = "tutor_id")
    private Tutor tutor;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "seeker_id", referencedColumnName = "seeker_id")
    private Seeker seeker;
}
