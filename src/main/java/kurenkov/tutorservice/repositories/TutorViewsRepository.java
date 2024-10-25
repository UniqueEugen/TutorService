package kurenkov.tutorservice.repositories;
import jakarta.transaction.Transactional;
import kurenkov.tutorservice.entities.TutorViews;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface TutorViewsRepository extends CrudRepository<TutorViews, Long> {

    @Query(value = "SELECT COUNT(*) > 0 FROM tutor_views WHERE user_id = :userId AND tutor_id = :tutorId", nativeQuery = true)
    boolean existsByUserIdAndTutorId(@Param("userId") Long userId, @Param("tutorId") Long tutorId);

    @Modifying
    @Query(value = "DELETE FROM tutor_views WHERE user_id = :userId AND tutor_id = :tutorId", nativeQuery = true)
    void deleteByUserIdAndTutorId(@Param("userId") Long userId, @Param("tutorId") Long tutorId);

    @Modifying
    @Query(value = "INSERT INTO tutor_views (user_id, tutor_id) VALUES (:userId, :tutorId)", nativeQuery = true)
    void insertTutorView(@Param("userId") Long userId, @Param("tutorId") Long tutorId);
}