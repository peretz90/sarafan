package peretz90.sarafan.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import peretz90.sarafan.domain.User;

public interface UserDetailsRepo extends JpaRepository<User, String> {
}
