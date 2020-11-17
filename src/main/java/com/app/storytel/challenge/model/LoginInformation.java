package com.app.storytel.challenge.model;

import java.io.Serializable;
import javax.persistence.AttributeOverride;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author samsonfagade
 */
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@Table(name = "login_information")
@AttributeOverride(name = "id", column = @Column(name = "login_id", nullable = false, columnDefinition = "BIGINT"))
public class LoginInformation extends StorytelModelBase implements Serializable {

    @NotNull(message = "Email Address cannot be blank")
    @Size(min = 8, max = 25)
    @Column(name = "email_address", unique = true)
    @Email(message = "Please provide a valid email address")
    private String emailAddress;
    @NotNull(message = "Password cannot be blank")
    @Size(min = 6, max = 12)
    @Column(name = "password")
    private String password;
    @JoinColumn(name = "app_role_id", referencedColumnName = "app_role_id")
    @ManyToOne
    private ApplicationRole applicationRole;
}
