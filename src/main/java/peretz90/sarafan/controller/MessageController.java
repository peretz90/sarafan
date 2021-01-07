package peretz90.sarafan.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import peretz90.sarafan.domain.Message;
import peretz90.sarafan.domain.Views;
import peretz90.sarafan.dto.EventType;
import peretz90.sarafan.dto.ObjectType;
import peretz90.sarafan.repo.MessageRepo;
import peretz90.sarafan.util.WsSender;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.BiConsumer;

@RestController
@RequestMapping("message")
public class MessageController {

  private final MessageRepo messageRepo;
  private final BiConsumer<EventType, Message> wsSender;

  @Autowired
  public MessageController(MessageRepo messageRepo, WsSender wsSender) {
    this.messageRepo = messageRepo;
    this.wsSender = wsSender.getSender(ObjectType.MESSAGE, Views.IdName.class);
  }

  @GetMapping
  @JsonView(Views.IdName.class)
  public List<Message> list() {
    return messageRepo.findAll();
  }

  @GetMapping("{id}")
  @JsonView(Views.FullMessage.class)
  public Message getOne(@PathVariable("id") Message message) {
    return message;
  }

  @PostMapping
  public Message create(@RequestBody Message message) {
    message.setCreationTime(LocalDateTime.now());
    Message updateMessage = messageRepo.save(message);
    wsSender.accept(EventType.CREATE, updateMessage);
    return updateMessage;
  }

  @PutMapping("{id}")
  public Message update(
      @PathVariable("id") Message messageFromDb,
      @RequestBody Message message
  ) {
    BeanUtils.copyProperties(message, messageFromDb, "id");

    Message updateMessage = messageRepo.save(messageFromDb);
    wsSender.accept(EventType.UPDATE, updateMessage);
    return updateMessage;
  }

  @DeleteMapping("{id}")
  public void delete(@PathVariable("id") Message message) {
    messageRepo.delete(message);
    wsSender.accept(EventType.REMOVE, message);
  }

}
