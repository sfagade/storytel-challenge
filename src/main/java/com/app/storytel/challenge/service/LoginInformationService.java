package com.app.storytel.challenge.service;

import com.app.storytel.challenge.model.ApplicationRole;
import com.app.storytel.challenge.model.LoginInformation;
import com.app.storytel.challenge.payload.request.LoginInformationRequest;
import com.app.storytel.challenge.respository.ApplicationRoleRepository;
import com.app.storytel.challenge.respository.LoginInformationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
@Slf4j
public class LoginInformationService {

    private final LoginInformationRepository loginInformationRepository;
    private final ApplicationRoleRepository applicationRoleRepository;

    @Autowired
    public LoginInformationService(LoginInformationRepository loginInformationRepository, ApplicationRoleRepository applicationRoleRepository) {
        this.loginInformationRepository = loginInformationRepository;
        this.applicationRoleRepository = applicationRoleRepository;
    }

    public LoginInformation saveNewLoginInformation(LoginInformationRequest loginInformationRequest) {

        if (loginInformationRequest != null && loginInformationRequest.getRoleId() != null) {

            Optional<ApplicationRole> optionalApplicationRole = this.applicationRoleRepository.findById(loginInformationRequest.getRoleId());
            if (optionalApplicationRole.isPresent()) {
                LoginInformation loginInformation = new LoginInformation();
                loginInformation.setEmailAddress(loginInformationRequest.getEmailAddress());
                loginInformation.setPassword(loginInformationRequest.getPassword());
                loginInformation.setApplicationRole(optionalApplicationRole.get());

                loginInformation = this.loginInformationRepository.save(loginInformation);

                if (loginInformation.getId() != null) {
                    return loginInformation;
                } else {
                    log.info("Failed to save Login-Information");
                }
            } else {
                log.info("Invalid role ID specified: {}", loginInformationRequest.getRoleId());
            }
        } else {
            log.info("Login-Request= {}", loginInformationRequest);
        }

        return null;
    }

    public LoginInformation findLoginInformation(Long loginId) {

        if (loginId != null) {
            Optional<LoginInformation> optionalLoginInformation = this.loginInformationRepository.findById(loginId);

            if (optionalLoginInformation.isPresent()) {
                return optionalLoginInformation.get();
            } else {
                log.info("Could not find login-information with ID {}", loginId);
            }
        } else {
            log.info("Login-ID= {}", loginId);
        }
        return null;
    }
}
