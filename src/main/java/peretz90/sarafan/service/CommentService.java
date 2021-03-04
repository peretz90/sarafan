package peretz90.sarafan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import peretz90.sarafan.domain.Comment;
import peretz90.sarafan.domain.User;
import peretz90.sarafan.domain.Views;
import peretz90.sarafan.dto.EventType;
import peretz90.sarafan.dto.ObjectType;
import peretz90.sarafan.repo.CommentRepo;
import peretz90.sarafan.util.WsSender;

import java.util.function.BiConsumer;

@Service
public class CommentService {

  private final CommentRepo commentRepo;
  private final BiConsumer<EventType, Comment> wsSender;

  @Autowired
  public CommentService(CommentRepo commentRepo, WsSender wsSender) {
    this.commentRepo = commentRepo;
    this.wsSender = wsSender.getSender(ObjectType.COMMENT, Views.FullComment.class);
  }

  public Comment create(Comment comment, User user) {
    comment.setAuthor(user);
    Comment commentFromDb = commentRepo.save(comment);

    wsSender.accept(EventType.CREATE, commentFromDb);

    return commentFromDb;
  }
}
