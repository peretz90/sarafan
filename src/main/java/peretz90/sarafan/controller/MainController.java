package peretz90.sarafan.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import peretz90.sarafan.domain.User;
import peretz90.sarafan.repo.MessageRepo;

import java.util.HashMap;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class MainController {

  private final MessageRepo messageRepo;

  @Value("${spring.profile.active}")
  private String profile;

  @GetMapping
  public String main(Model model, @AuthenticationPrincipal User user) {

    HashMap<Object, Object> data = new HashMap<>();

    if (user != null) {
      data.put("profile", user);
      data.put("messages", messageRepo.findAll());
    }

    model.addAttribute("frontendData", data);
    model.addAttribute("isDevMode", "dev".equals(profile));

    return "index";
  }

}
