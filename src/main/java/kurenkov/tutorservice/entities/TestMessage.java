package kurenkov.tutorservice.entities;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestMessage {

    private MessageType type;
    private String content;
    private String sender;

}