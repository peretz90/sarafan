package peretz90.sarafan.repo;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import peretz90.sarafan.domain.Message;

import java.util.List;

@Repository
public interface MessageRepo extends JpaRepository<Message, Long> {

  @EntityGraph(attributePaths = { "comments" })
  List<Message> findAll();

}
