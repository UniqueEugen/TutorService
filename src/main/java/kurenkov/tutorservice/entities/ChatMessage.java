package kurenkov.tutorservice.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "order_message")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "message")
    private String message;

    @Column(name = "date")
    private java.sql.Date date;

    @Column(name = "time")
    private java.sql.Time time;

    @Column(name= "type")
    private boolean type;

    @Column(name = "chat_message_id")
    private Long chatId;
    /*@Column(name = "status", columnDefinition = "varchar")
    private MessageStatus status;*/
}
