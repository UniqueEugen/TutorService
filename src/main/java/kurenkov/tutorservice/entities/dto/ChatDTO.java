package kurenkov.tutorservice.entities.dto;

import jakarta.persistence.*;
import kurenkov.tutorservice.entities.ChatMessage;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ChatDTO {

    private Long id;

    private Long tutor;

    private Long seeker;

    private SeekerDataDTO seekerData;
    private TutorDataDTO tutorData;
}
