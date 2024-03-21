package kurenkov.tutorservice.repositories;

import kurenkov.tutorservice.entities.OrderChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderChatRepository extends JpaRepository<OrderChat, Long> {
}
