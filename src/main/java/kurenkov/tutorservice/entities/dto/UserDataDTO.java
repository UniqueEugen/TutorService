package kurenkov.tutorservice.entities.dto;

import jakarta.persistence.*;
import kurenkov.tutorservice.entities.Seeker;
import kurenkov.tutorservice.entities.Tutor;
import kurenkov.tutorservice.entities.User;
import lombok.Data;


@Data
public class UserDataDTO {

    private Long id;


    private String name;

    private String surname;

    private String secName;

    private String eMail;

    private String phone;

    private int age;

    private TutorDTO tutor;

    private SeekerDTO seeker;

    private UserDTO user;
}
