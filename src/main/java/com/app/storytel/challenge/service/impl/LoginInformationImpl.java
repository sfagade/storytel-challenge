package com.app.storytel.challenge.service.impl;

import com.app.storytel.challenge.model.ApplicationRole;
import com.app.storytel.challenge.model.LoginInformation;
import com.app.storytel.challenge.payload.request.LoginInformationRequest;
import com.app.storytel.challenge.respository.ApplicationRoleRepository;
import com.app.storytel.challenge.respository.LoginInformationRepository;
import com.app.storytel.challenge.service.LoginInformationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * This class is used to implement all CRUD methods declared in
 * LoginInformationService interface
 */
@Service
@Slf4j
public class LoginInformationImpl implements LoginInformationService {

    private final LoginInformationRepository loginInformationRepository;
    private final ApplicationRoleRepository applicationRoleRepository;

    @Autowired
    public LoginInformationImpl(LoginInformationRepository loginInformationRepository,
            ApplicationRoleRepository applicationRoleRepository) {
        this.loginInformationRepository = loginInformationRepository;
        this.applicationRoleRepository = applicationRoleRepository;
    }

    @Override
    public LoginInformation saveNewLoginInformation(LoginInformationRequest loginInformationRequest) {

        if (loginInformationRequest != null && loginInformationRequest.getRoleId() != null) {

            Optional<ApplicationRole> optionalApplicationRole
                    = this.applicationRoleRepository.findById(loginInformationRequest.getRoleId());

            if (optionalApplicationRole.isPresent()) {
                LoginInformation loginInformation
                        = new LoginInformation(null, loginInformationRequest.getEmailAddress(),
                                loginInformationRequest.getPassword(), optionalApplicationRole.get(),
                                null, null);
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

    @Override
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
