package com.app.storytel.challenge.payload.response;

import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author samsonfagade
 */
@Data
@NoArgsConstructor
public class ErrorMessage {

    private String message;
    private Date errorDate;

    public ErrorMessage(String message, Date errorDate) {
        this.message = message;
        this.errorDate = errorDate;
    }

}
