package yte.app.application.authentication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import yte.app.application.authentication.entity.Users;
import yte.app.application.authentication.repository.UserRepository;
import yte.app.application.common.response.MessageResponse;
import yte.app.application.common.response.ResponseType;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public MessageResponse addUsers(Users user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
       // user.getAuthorities().add()

        userRepository.save(user);

        return new MessageResponse(ResponseType.SUCCESS, "User has been added successfully");
    }

    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    public Users getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    public MessageResponse deleteUserById(Long id) {
        userRepository.deleteById(id);

        return new MessageResponse(ResponseType.SUCCESS, "User has been deleted successfully");
    }

    public MessageResponse updateUser(Long id, Users updatedUser) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        user.update(updatedUser);

        userRepository.save(user);

        return new MessageResponse(ResponseType.SUCCESS, "User has been updated successfully");
    }
}
