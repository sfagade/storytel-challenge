package com.app.storytel.challenge.payload.response;

import java.time.LocalDateTime;
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
    private LocalDateTime created;
    private LocalDateTime modified;
}
