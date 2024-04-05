package kurenkov.tutorservice.entities.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import kurenkov.tutorservice.entities.Seeker;
import lombok.*;

import java.sql.Date;
import java.sql.Time;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDataDTO {
    private Long id;

    private Date date;

    private Time time;

    private String status;


    private Long tutor;

    private Long seeker;

    private Seeker seekerData;
    private TutorDataDTO tutorData;
}
