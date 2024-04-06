package kurenkov.tutorservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "time", nullable = false)
    private Time time;

    @Column(name = "status", nullable = false)
    private String status;



    /*@ManyToOne
    @JoinColumn(name = "tutor_order")
    private Tutor tutor;

    @ManyToOne
    @JoinColumn(name = "seeker_order")
    private Seeker seeker;*/

    @Column(name = "tutor_order")
    private Long tutor;

    @Column(name = "seeker_order")
    private Long seeker;

}
