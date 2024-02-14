package kurenkov.tutorservice.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "forum_message")
@Data
public class ForumMessage {
    @Id
    @Column(name = "message_id", nullable = false)
    @GeneratedValue
    private Long id;

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name="date", nullable = false)
    private java.sql.Date date;

    @Column(name = "time", nullable = false)
    private java.sql.Time time;

    @ManyToOne
    private UserData userData;

}
