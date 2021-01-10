package peretz90.sarafan.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import peretz90.sarafan.domain.User;
import peretz90.sarafan.domain.Views;
import peretz90.sarafan.repo.MessageRepo;

import java.util.HashMap;

@Controller
@RequestMapping("/")
public class MainController {

  private final MessageRepo messageRepo;

  @Value("${spring.profile.active}")
  private String profile;
  private final ObjectWriter writer;

  @Autowired
  public MainController(
      MessageRepo messageRepo,
      ObjectMapper mapper
  ) {
    this.messageRepo = messageRepo;
    this.writer = mapper
        .setConfig(mapper.getSerializationConfig())
        .writerWithView(Views.FullMessage.class);
  }

  @GetMapping
  public String main(
      Model model,
      @AuthenticationPrincipal User user
  ) throws JsonProcessingException {

    HashMap<Object, Object> data = new HashMap<>();

    if (user != null) {
      data.put("profile", user);

      String messages = writer.writeValueAsString(messageRepo.findAll());
      model.addAttribute("messages", messages);
    }

    model.addAttribute("frontendData", data);
    model.addAttribute("isDevMode", "dev".equals(profile));

    return "index";
  }

}
