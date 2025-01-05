package kurenkov.tutorservice.entities.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TutorDataDTOFav extends TutorDataDTO {
    private boolean isFavorite;
    private double rating;

    public TutorDataDTOFav(TutorDataDTO tutorDataDTO, boolean isFavorite, double rating) {
        super(tutorDataDTO.id, tutorDataDTO.name, tutorDataDTO.surname,
                tutorDataDTO.secName, tutorDataDTO.specialisation,
                tutorDataDTO.price, tutorDataDTO.description,
                tutorDataDTO.address, tutorDataDTO.photo,
                tutorDataDTO.phone, tutorDataDTO.email, tutorDataDTO.tutorId);
        this.isFavorite = isFavorite;
        this.rating = rating;
    }
}
