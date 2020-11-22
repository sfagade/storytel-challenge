package com.app.storytel.challenge.payload.response;

import lombok.Data;

/**
 * This dto class is used to send the newly logged in info back to the client
 */
@Data
public class JwtAuthenticationResponse {

    private String accessToken;
    private String tokenType = "Bearer";

    public JwtAuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
