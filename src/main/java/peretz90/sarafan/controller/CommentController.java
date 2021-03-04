package peretz90.sarafan.controller;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import peretz90.sarafan.domain.Comment;
import peretz90.sarafan.domain.User;
import peretz90.sarafan.domain.Views;
import peretz90.sarafan.service.CommentService;

@RestController
@RequestMapping("comment")
@RequiredArgsConstructor
public class CommentController {

  private final CommentService commentService;

  @PostMapping
  @JsonView(Views.FullComment.class)
  public Comment create(
      @RequestBody Comment comment,
      @AuthenticationPrincipal User user
  ) {
    return commentService.create(comment, user);
  }

}
