package kurenkov.tutorservice.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "order_chat")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderChat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "chat_message_id")
    private List<ChatMessage> chatMessages=new ArrayList<>();

    @Column(name = "tutor_id")
    private Long tutor;
    @Column(name = "seeker_id")
    private Long seeker;

}
