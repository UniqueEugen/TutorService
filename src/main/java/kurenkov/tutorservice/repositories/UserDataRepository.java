package kurenkov.tutorservice.repositories;

import kurenkov.tutorservice.entities.UserData;
import kurenkov.tutorservice.entities.dto.TutorDataDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDataRepository extends JpaRepository<UserData, Long> {
    @Query("SELECT new kurenkov.tutorservice.entities.dto.TutorDataDTO(u.id, u.name, u.surname, u.secName, u.tutor.specialisation, u.tutor.price, u.tutor.description, u.tutor.address, u.tutor.photo) FROM UserData u")
    List<TutorDataDTO> findNamesAndSurnamesWithTutor();
}
