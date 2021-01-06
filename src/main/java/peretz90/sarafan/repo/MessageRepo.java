package peretz90.sarafan.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import peretz90.sarafan.domain.Message;

public interface MessageRepo extends JpaRepository<Message, Long> {
}
