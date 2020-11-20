package com.app.storytel.challenge.resources;

import com.app.storytel.challenge.model.ApplicationRole;
import com.app.storytel.challenge.model.LoginInformation;
import com.app.storytel.challenge.payload.request.LoginInformationRequest;
import com.app.storytel.challenge.service.LoginInformationService;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class AuthenticationResourceTest {

    @MockBean
    private LoginInformationService loginInformationService;

    @Test
    void authenticateUser() {
    }

    @Test
    void registerUser() {

        LoginInformationRequest loginInformationRequest = new LoginInformationRequest();
        LoginInformation loginInformation = new LoginInformation();
        loginInformation.setId(2L);
        loginInformation.setEmailAddress("test.example@yahoo.com");
        loginInformation.setPassword("encrypted-password");
        loginInformation.setApplicationRole(new ApplicationRole());
        loginInformation.setCreated(LocalDateTime.MAX);
        loginInformation.setModified(LocalDateTime.MAX);

        Mockito.when(this.loginInformationService.saveNewLoginInformation(loginInformationRequest)).thenReturn(loginInformation);
    }
}
