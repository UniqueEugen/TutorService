package kurenkov.tutorservice.entities.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {

    private Long id;


    private String country;


    private String city;


    private String street;


    private String house;


    private String office;

   /* private boolean isMicro;*/

}