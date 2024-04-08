package kurenkov.tutorservice.entities.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MessagePayload {
    private String message;

    public void setMessage(String message) {
        this.message = message;
    }
}
