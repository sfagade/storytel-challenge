package com.app.storytel.challenge.payload.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This dto class is used to return login-information object data back to the
 * REST client. * This provides a safe a friendly way to make sure we're only
 * sharing data * that the client cares about
 *
 * @author samsonfagade
 */
@NoArgsConstructor
@Data
public class LoginInformationResponse {

    private Long id;
    private String emailAddress;
    private String roleName;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime created;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime modified;

    public LoginInformationResponse(Long id, String emailAddress, String roleName, LocalDateTime created,
            LocalDateTime modified) {
        this.id = id;
        this.emailAddress = emailAddress;
        this.created = created;
        this.modified = modified;
        this.roleName = roleName;
    }

}
