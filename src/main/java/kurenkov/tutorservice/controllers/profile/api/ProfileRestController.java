package kurenkov.tutorservice.controllers.profile.api;


import kurenkov.tutorservice.entities.Comment;
import kurenkov.tutorservice.entities.Tutor;
import kurenkov.tutorservice.entities.UserData;
import kurenkov.tutorservice.services.CommentService;
import kurenkov.tutorservice.services.TutorService;
import kurenkov.tutorservice.services.UserDataService;
import kurenkov.tutorservice.services.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/profile/api")
public class ProfileRestController {

    private final CommentService commentService;

    private final UserDataService userDataService;

    private final TutorService tutorService;

    public ProfileRestController(CommentService commentService,
                                 UserDataService userDataService,
                                 TutorService tutorService) {
        this.commentService = commentService;
        this.userDataService = userDataService;
        this.tutorService = tutorService;
    }

    // Получить комментарии по идентификатору репетитора
    @GetMapping("/getcomments")
    public List<CommentResponse> getCommentsByTutorId(@RequestParam Long tutorId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Tutor tutor = userDataService.getUserDataById(tutorId).getTutor();
        //Long id = tutorService.getTutorById(tutor.getId()).getId();
        List<Comment> comments = commentService.findByTutor(tutor);
        List<CommentResponse> commentResponses = new ArrayList<>();
        for (Comment comment : comments) {
            CommentResponse commentResponse = new CommentResponse();
            commentResponse.setComment(comment.getComment());
            commentResponse.setRating(comment.getRating());
            commentResponse.setUserId(comment.getUserData().getId());
            commentResponse.setDateTime(comment.getCreatedAt());
            commentResponses.add(commentResponse);
        }
        return commentResponses;
    }

    @PostMapping(value = "/addcomment", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addComment(@RequestBody CommentRequest request) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserData currentUser = userDataService.loadUserDataByUsername(authentication.getName());
            Comment comment = new Comment();
            comment.setUserData(currentUser);
            comment.setRating(request.rating);
            comment.setComment(request.comment);
            comment.setTutor(tutorService.getTutorById(request.tutor_id));
            commentService.save(comment);
            /*Comment comment = commentClass.getComment();
            comment.setTutor(tutorService.getTutorById(commentClass.getTutor_id()));
            comment.setUserData(currentUser);
            commentService.save(commentClass.comment);*/
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private static class CommentRequest {
        public Long tutor_id;
        public int rating;
        public String comment;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    private class CommentResponse {
        public String comment;
        public int rating;
        public LocalDateTime dateTime;
        public Long userId;
    }
}
