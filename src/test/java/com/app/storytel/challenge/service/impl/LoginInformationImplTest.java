package com.app.storytel.challenge.service.impl;

import com.app.storytel.challenge.model.ApplicationRole;
import com.app.storytel.challenge.model.LoginInformation;
import com.app.storytel.challenge.payload.request.LoginInformationRequest;
import com.app.storytel.challenge.respository.ApplicationRoleRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LoginInformationImplTest {

    @Autowired
    private LoginInformationImpl loginInformationImpl;
    @Autowired
    private ApplicationRoleRepository applicationRoleRepository;

    @BeforeAll
    void initTestRecords() {
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
                this.loginInformationImpl.saveNewLoginInformation(loginInformationRequest);
        assertNotNull(loginInformation, "Failed to save Login-Information");
        assertNotNull(loginInformation.getId(),"Login-Information returned without ID");

    }

}