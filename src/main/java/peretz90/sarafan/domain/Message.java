package peretz90.sarafan.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
@Data
public class Message {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonView(Views.Id.class)
  private Long id;

  @JsonView(Views.IdName.class)
  private String text;

  @Column(updatable = false)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonView(Views.FullMessage.class)
  private LocalDateTime creationTime;

  @ManyToOne
  @JoinColumn(name = "user_id")
  @JsonView(Views.FullMessage.class)
  private User author;

  @OneToMany(mappedBy = "message", cascade = CascadeType.ALL)
  @JsonView(Views.FullMessage.class)
  private List<Comment> comments;

  @JsonView(Views.FullMessage.class)
  private String link;
  @JsonView(Views.FullMessage.class)
  private String linkTitle;
  @JsonView(Views.FullMessage.class)
  private String linkDescription;
  @JsonView(Views.FullMessage.class)
  private String linkCover;

}
