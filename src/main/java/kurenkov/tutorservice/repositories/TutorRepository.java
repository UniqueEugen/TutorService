package kurenkov.tutorservice.repositories;

import kurenkov.tutorservice.entities.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TutorRepository extends JpaRepository<Tutor, Long> {
    @Query(value = """
            SELECT t.tutor_id
            FROM tutor t
            WHERE t.tutor_id NOT IN (
                SELECT tutor_id
                FROM tutor_views
                WHERE user_id = :userId
            )
            ORDER BY
                (CASE
                     WHEN t.specialisation IN (
                         SELECT DISTINCT t2.specialisation
                         FROM tutor t2
                         WHERE t2.tutor_id IN (
                             SELECT tutor_id
                             FROM tutor_views
                             WHERE user_id = :userId
                         )
                     ) THEN 0
                     ELSE 1
                END) ASC,
                t.price ASC
            LIMIT 4
            """, nativeQuery = true)
    List<Long> findTutorsByUserId(@Param("userId") Long userId);
}
