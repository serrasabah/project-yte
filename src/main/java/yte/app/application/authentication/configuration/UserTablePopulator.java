package yte.app.application.authentication.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import yte.app.application.authentication.repository.UserRepository;
import yte.app.application.authentication.entity.Authority;
import yte.app.application.authentication.entity.Users;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserTablePopulator {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;



    @PostConstruct
    public void populateDatabase() {
        if (!userRepository.existsByUsername("user")) {
            Users user = new Users("user", passwordEncoder.encode("user"), List.of(new Authority("USER")));
            userRepository.save(user);
        }
        if (!userRepository.existsByUsername("admin")) {
            Users user = new Users("admin", passwordEncoder.encode("admin"), List.of(new Authority("ADMIN")));
            userRepository.save(user);
        }
    }

}