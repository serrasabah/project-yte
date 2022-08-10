package yte.app.application.authentication.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import yte.app.application.authentication.controller.request.AddUserRequest;
import yte.app.application.authentication.controller.request.LoginRequest;
import yte.app.application.authentication.controller.responses.UserQueryModel;
import yte.app.application.authentication.service.UserService;
import yte.app.application.common.response.MessageResponse;
import yte.app.application.authentication.service.LoginService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    @PostMapping()
    public MessageResponse addUsers(@Valid @RequestBody AddUserRequest adduser) {
        return userService.addUsers(adduser.toDomainEntity());
    }



    @GetMapping()
    public List<UserQueryModel> getAllStudents() {
        return userService.getAllUsers()
                .stream()
                .map(user -> new UserQueryModel(user))
                .toList();
    }

    @GetMapping("/{id}")
    public UserQueryModel getById(@NotNull @PathVariable Long id) {
        return new UserQueryModel(userService.getById(id));
    }

    @DeleteMapping("/{id}")
    public MessageResponse deleteUserbyId(@PathVariable @NotNull Long id) {
        return userService.deleteUserById(id);
    }




}