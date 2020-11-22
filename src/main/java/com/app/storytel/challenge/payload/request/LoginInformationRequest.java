package com.app.storytel.challenge.payload.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Dto class to retrieve data from REST clients, this also provides a convenient
 * way to provide data validation on the requests
 *
 * @author samsonfagade
 */
@NoArgsConstructor
@Data
public class LoginInformationRequest {

    @NotNull(message = "Email Address cannot be blank")
    @Size(min = 8, max = 25)
    @Email(message = "Please provide a valid email address")
    private String emailAddress;
    @NotNull(message = "Password cannot be blank")
    @Size(min = 6, max = 50)
    @Basic(optional = false)
    private String password;
    @NotNull(message = "User role cannot be blank")
    private Long roleId;

    public LoginInformationRequest(String emailAddress, String password, Long roleId) {
        this.emailAddress = emailAddress;
        this.password = password;
        this.roleId = roleId;
    }
}
