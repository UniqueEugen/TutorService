package kurenkov.tutorservice.entities;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WebMessage {
    private Long id;
    private String message;
    private boolean type;
}
