package kurenkov.tutorservice.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_chat")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
