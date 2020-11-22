package com.app.storytel.challenge.payload.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
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
