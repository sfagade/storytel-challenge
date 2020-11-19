package com.app.storytel.challenge.resources;

import com.app.storytel.challenge.service.LoginInformationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/loginInfo")
public class LoginInformationResource {

    private final LoginInformationService loginInformationService;

    public LoginInformationResource(LoginInformationService loginInformationService) {
        this.loginInformationService = loginInformationService;
    }
}
