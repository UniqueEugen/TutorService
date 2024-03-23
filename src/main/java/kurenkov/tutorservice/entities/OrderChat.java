package kurenkov.tutorservice.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_chat")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderChat {
    @Id
    @GeneratedValue
    @Column
    private Long id;

    @Column(name = "message")
    private String message;

    @Column(name = "date")
    private java.sql.Date date;

    @Column(name = "time")
    private java.sql.Time time;

}
