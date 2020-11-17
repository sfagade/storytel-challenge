package com.app.storytel.challenge.service;

import com.app.storytel.challenge.model.ApplicationRole;
import com.app.storytel.challenge.model.LoginInformation;
import com.app.storytel.challenge.payload.request.LoginInformationRequest;
import com.app.storytel.challenge.respository.ApplicationRoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LoginInformationServiceTest {

    @Autowired
    private LoginInformationService loginInformationService;
    @Autowired
    private ApplicationRoleRepository applicationRoleRepository;

    @BeforeEach
    void setUp() {
        ApplicationRole applicationRole = new ApplicationRole();
        applicationRole.setRoleName("USER");
        applicationRole.setRoleDescription("DESCRIPTION OF WHAT THE ROLE IS ABOUT");

        this.applicationRoleRepository.save(applicationRole);
    }

    @Test
    void saveNewLoginInformationTest() {
        LoginInformationRequest loginInformationRequest = new LoginInformationRequest();
        loginInformationRequest.setEmailAddress("cullen@example.com");
        loginInformationRequest.setPassword("the-password");
        loginInformationRequest.setRoleId(1L);

        LoginInformation loginInformation =
                this.loginInformationService.saveNewLoginInformation(loginInformationRequest);
        assertNotNull(loginInformation, "Failed to save Login-Information");
        assertNotNull(loginInformation.getId(),"Login-Information returned without ID");

    }

}