package peretz90.sarafan.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peretz90.sarafan.domain.Comment;
import peretz90.sarafan.domain.User;
import peretz90.sarafan.repo.CommentRepo;

@Service
@RequiredArgsConstructor
public class CommentService {

  private final CommentRepo commentRepo;

  public Comment create(Comment comment, User user) {
    comment.setAuthor(user);
    commentRepo.save(comment);

    return comment;
  }
}
