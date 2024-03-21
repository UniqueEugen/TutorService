package kurenkov.tutorservice.repositories;

import kurenkov.tutorservice.entities.ForumMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForumMessageRepository extends JpaRepository<ForumMessage, Long> {
}
