package kurenkov.tutorservice.services;

import kurenkov.tutorservice.repositories.ForumMessageRepository;
import kurenkov.tutorservice.entities.ForumMessage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForumMessageService {
    private final ForumMessageRepository forumMessageRepository;

    public ForumMessageService(ForumMessageRepository forumMessageRepository) {
        this.forumMessageRepository = forumMessageRepository;
    }

    public List<ForumMessage> getAllForumMessages() {
        return forumMessageRepository.findAll();
    }

    public ForumMessage getForumMessageById(Long id) {
        return forumMessageRepository.findById(id).orElse(null);
    }

    public ForumMessage saveForumMessage(ForumMessage forumMessage) {
        return forumMessageRepository.save(forumMessage);
    }

    public void deleteForumMessage(Long id) {
        forumMessageRepository.deleteById(id);
    }
}
