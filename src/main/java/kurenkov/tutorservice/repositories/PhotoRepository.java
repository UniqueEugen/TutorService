package kurenkov.tutorservice.repositories;

import kurenkov.tutorservice.entities.Order;
import kurenkov.tutorservice.entities.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
}
