package kurenkov.tutorservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tutor_views")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TutorViews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Уникальный идентификатор

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "tutor_id", nullable = false)
    private Tutor  tutor; // Сущность наставника

    @Column(name = "viewed_at")
    private LocalDateTime viewedAt;

    @PrePersist
    protected void onCreate() {
        viewedAt = LocalDateTime.now();
    }

}