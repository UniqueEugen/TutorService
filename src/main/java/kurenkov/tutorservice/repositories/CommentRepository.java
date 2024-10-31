package kurenkov.tutorservice.repositories;

import kurenkov.tutorservice.entities.Comment;
import kurenkov.tutorservice.entities.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByTutor(Tutor tutor);
}