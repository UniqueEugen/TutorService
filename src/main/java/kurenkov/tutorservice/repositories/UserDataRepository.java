package kurenkov.tutorservice.repositories;

import jakarta.transaction.Transactional;
import kurenkov.tutorservice.entities.Tutor;
import kurenkov.tutorservice.entities.UserData;
import kurenkov.tutorservice.entities.dto.TutorDataDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDataRepository extends JpaRepository<UserData, Long> {
    @Query("SELECT new kurenkov.tutorservice.entities.dto.TutorDataDTO(u.id, u.name, u.surname, u.secName, u.tutor.specialisation, u.tutor.price, u.tutor.description, u.tutor.address, u.tutor.photo) FROM UserData u")
    List<TutorDataDTO> findNamesAndSurnamesWithTutor();

    UserData findByTutor(Tutor tutor);

    @Modifying
    @Query("SELECT u FROM UserData u JOIN u.tutor t WHERE t.id IN :tutorIds")
    List<UserData> findUsersByTutorIds(@Param("tutorIds") List<Long> tutorIds);

    UserData findByUserId(Long userId);
}
