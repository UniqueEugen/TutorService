package kurenkov.tutorservice.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "order")
@Data
public class Order {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "date", nullable = false)
    private java.sql.Date date;

    @Column(name = "time", nullable = false)
    private java.sql.Time time;

    @Column(name = "status", nullable = false)
    private String status;
}
