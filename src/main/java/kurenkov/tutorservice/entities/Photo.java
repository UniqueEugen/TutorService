package kurenkov.tutorservice.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "photos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "filename")
    private String filename;

    @Lob
    @Column(name = "content")
    private byte[] content;
}
