package com.app.storytel.challenge.component;


import com.app.storytel.challenge.auth.UserPrincipal;
import com.app.storytel.challenge.model.LoginInformation;
import com.app.storytel.challenge.service.LoginInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * This class is used as Helper class for getting logged in user profile info
 *
 * @author sfagade
 */
@Component
public class LoginInformationComponent {

    @Autowired
    private LoginInformationService loginInformationService;

    /**
     * This method is used to fetch the current user's login info from auth
     * token provided as part of this request
     *
     * @return LoginInformation
     */
    public LoginInformation fetchLoginUserInformation() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal currentPrincipal = (UserPrincipal) authentication.getPrincipal();
        return loginInformationService.findLoginInformation(currentPrincipal.getId());
    }
}
