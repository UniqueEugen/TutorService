package kurenkov.tutorservice.repositories;

import kurenkov.tutorservice.entities.Seeker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeekerRepository extends JpaRepository<Seeker, Long> {
}
