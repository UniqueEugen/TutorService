package kurenkov.tutorservice.entities.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhotoDTO {

    private Long id;

    private String filename;


    private String content;
}
