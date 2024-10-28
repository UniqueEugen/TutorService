package kurenkov.tutorservice.repositories;

import kurenkov.tutorservice.entities.PageVisit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VisitsRepository extends JpaRepository<PageVisit, Long> {

    @Query("SELECT TO_CHAR(v.visitDate, 'YYYY-MM-DD'), COUNT(v) FROM PageVisit v WHERE v.tutor.id = :tutorId GROUP BY v.visitDate ORDER BY v.visitDate")
    List<Object[]> findVisitsGroupedByDate(@Param("tutorId") Long tutorId);
}
