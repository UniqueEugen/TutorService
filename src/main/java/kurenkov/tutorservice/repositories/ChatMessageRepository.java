package kurenkov.tutorservice.repositories;

import kurenkov.tutorservice.entities.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    // Дополнительные методы репозитория, если необходимо
/*    List<ChatMessage> findByChatMessageId(Long orderChatId);*/
}
