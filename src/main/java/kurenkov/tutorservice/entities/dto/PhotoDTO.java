package kurenkov.tutorservice.entities.dto;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PhotoDTO {

    private Long id;

    private String filename;


    private String content;
}
