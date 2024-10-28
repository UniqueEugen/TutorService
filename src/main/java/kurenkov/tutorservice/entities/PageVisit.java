package kurenkov.tutorservice.entities;


import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "page_visits")
@Getter
@Setter
@AllArgsConstructor
@ToString
public class PageVisit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tutor_id", nullable = false)
    private Tutor tutor;

    @Column(name = "visit_date", nullable = false, updatable = false)
    private LocalDate visitDate;

    // Конструктор по умолчанию
    public PageVisit() {
        this.visitDate = LocalDate.now(); // Устанавливаем текущую дату по умолчанию
    }

}
