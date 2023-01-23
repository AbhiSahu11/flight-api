package nl.abnamro.api.flightsearch.runner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.abnamro.api.flightsearch.domain.Role;
import nl.abnamro.api.flightsearch.domain.User;
import nl.abnamro.api.flightsearch.repository.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Profile("!test")
@RequiredArgsConstructor
@Component
public class UserCreationRunner implements ApplicationRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder ;
    @Override
    public void run(ApplicationArguments args) {
        try {
                ObjectMapper mapper = new ObjectMapper();
                TypeReference<List<User>> typeReference = new TypeReference<>() {};
                List<User> users= saveRoleAndEncryptPassword(mapper,typeReference);
                userRepository.saveAll(users);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private List<User> saveRoleAndEncryptPassword(ObjectMapper mapper, TypeReference<List<User>> typeReference ) throws IOException {
        InputStream inputStream = TypeReference.class.getResourceAsStream("/users.json");
        return mapper.readValue(inputStream, typeReference).
                stream().map(user -> {
                    user.setPassword(passwordEncoder.encode(user.getPassword()));
                    user.setRole(Role.ROLE_USER);
                    return user;
                }).collect(Collectors.toList());
    }
}
