package nl.abnamro.api.flightsearch.repository;


import nl.abnamro.api.flightsearch.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUserName(String userName);

}
