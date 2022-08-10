package yte.app.application.authentication.controller.request;

import yte.app.application.authentication.entity.Users;

public record UpdateUserRequest(
        String username,

        String password,

        String role
) {

        public Users toDomainEntity() {

            return new Users(username, password, role);
        }
}
