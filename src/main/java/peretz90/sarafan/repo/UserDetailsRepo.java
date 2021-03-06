package peretz90.sarafan.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import peretz90.sarafan.domain.User;

@Repository
public interface UserDetailsRepo extends JpaRepository<User, String> {
}
