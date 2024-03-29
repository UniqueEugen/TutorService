package kurenkov.tutorservice.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "forum_message")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ForumMessage {
    @Id
    @Column(name = "message_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name="date", nullable = false)
    private java.sql.Date date;

    @Column(name = "time", nullable = false)
    private java.sql.Time time;

    @ManyToOne
    @JoinColumn(name = "user_data_id")
    private UserData userData;

}
