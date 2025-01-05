package kurenkov.tutorservice.controllers.profile.api;


import kurenkov.tutorservice.entities.Comment;
import kurenkov.tutorservice.entities.Tutor;
import kurenkov.tutorservice.entities.UserData;
import kurenkov.tutorservice.entities.dto.UserDataDTO;
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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
            CommentResponse commentResponse = new CommentResponse(
                    comment.getId(),
                    comment.getComment(),
                    comment.getRating(),
                    comment.getCreatedAt(),
                    comment.getUserData().getId(),
                    comment.getUserData().getName() + " " + comment.getUserData().getSurname());
            /*CommentResponse commentResponse = new CommentResponse();
            commentResponse.setComment(comment.getComment());
            commentResponse.setRating(comment.getRating());
            commentResponse.setUserId(comment.getUserData().getId());
            commentResponse.setDateTime(comment.getCreatedAt());*/
            commentResponses.add(commentResponse);
        }
        return commentResponses;
    }

    @GetMapping("/getuserid")
    public Long getUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = userDataService.loadUserDataByUsername(authentication.getName()).getId();
        //List<Long> userIds = new ArrayList<>();
        //userIds.add(id);
        return id;
    }

    @PostMapping(value = "/addcomment", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addComment(@RequestBody CommentRequest request) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserData currentUser = userDataService.loadUserDataByUsername(authentication.getName());
            List<Comment> existsComments = commentService.findByUserData(currentUser);
            Tutor currentTutor = userDataService.getUserDataById(request.tutor_id).getTutor();
            // Проверяем, есть ли комментарий к текущему репетитору
            Comment existsComment = null;
            for (Comment comment : existsComments) {
                if (comment.getTutor().getId().equals(currentTutor.getId())) {
                    existsComment = comment;
                    break;
                }
            }
            Comment comment = new Comment();
            if (existsComment == null) {
                comment.setUserData(currentUser);
                System.out.println(request.tutor_id);
                comment.setTutor(currentTutor);
            }else {
                comment = existsComment;
            }
            comment.setRating(request.rating);
            comment.setComment(request.comment);
            commentService.save(comment);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/deletecomment/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
        try {
            // Check if the comment exists
            Comment comment = commentService.findById(commentId);
            if (comment == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment not found");
            }

            // Delete the comment
            commentService.deleteById(commentId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/getrating")
    public double getRating(@RequestParam Long tutorId) {
        Tutor tutor = userDataService.getUserDataById(tutorId).getTutor();
        List<Comment> comments = commentService.findByTutor(tutor);
        double rating = 0;
        int counter = 0;

        for (Comment comment : comments) {
            rating += comment.getRating();
            counter++;
        }

        // Проверяем, есть ли комментарии
        if (counter > 0) {
            rating = rating / counter;
        } else {
            return 0; // Или любое другое значение по умолчанию, если комментариев нет
        }

        // Округляем до сотых
        BigDecimal roundedRating = new BigDecimal(rating).setScale(2, RoundingMode.HALF_UP);
        return roundedRating.doubleValue();
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
        public Long commentId;
        public String comment;
        public int rating;
        public LocalDateTime dateTime;
        public Long userId;
        public String nameSurname;
    }
}
