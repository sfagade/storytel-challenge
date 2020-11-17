package com.app.storytel.challenge.payload.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Data
public class LoginInformationRequest {

    @NotNull(message = "Email Address cannot be blank")
    @Size(min = 8, max = 25)
    @Email(message = "Please provide a valid email address")
    private String emailAddress;
    @NotNull(message = "Password cannot be blank")
    @Size(min = 6, max = 12)
    private String password;
    @NotNull(message = "User role cannot be blank")
    private Long roleId;
}
