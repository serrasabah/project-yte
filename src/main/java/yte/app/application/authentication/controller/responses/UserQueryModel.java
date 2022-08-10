package yte.app.application.authentication.controller.responses;


import yte.app.application.authentication.entity.Users;

public record UserQueryModel(
        Long id,
        String username,
        String password,
        String role
) {

    public UserQueryModel(Users user) {
        this(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getRole()
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                

        );
    }
}

