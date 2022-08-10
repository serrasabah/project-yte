package yte.app.application.authentication.controller.request;


import yte.app.application.authentication.entity.Users;

import javax.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank
        String username,
        @NotBlank
        String password,

        String role


) {
        public Users toDomainEntity() {

                return new Users(username,  password, role);
        }

}