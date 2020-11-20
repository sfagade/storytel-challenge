package com.app.storytel.challenge.auth;

import com.app.storytel.challenge.model.LoginInformation;
import com.app.storytel.challenge.respository.LoginInformationRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author samsonfagade
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final LoginInformationRepository loginInformationRepository;

    @Autowired
    public CustomUserDetailsService(LoginInformationRepository loginInformationRepos) {
        this.loginInformationRepository = loginInformationRepos;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Let people login with either username or email
        LoginInformation loginInformation = loginInformationRepository.findOneByEmailAddress(username);

        if (loginInformation == null) {
            throw new UsernameNotFoundException("No user found with given username : " + username);
        }

        return UserPrincipal.create(loginInformation);
    }

    public UserDetails loadUserById(Long id) {
        Optional<LoginInformation> optionalLoginInfo = loginInformationRepository.findById(id);
        LoginInformation loginInfo;

        if (optionalLoginInfo.isPresent()) {
            loginInfo = optionalLoginInfo.get();
        } else {
            throw new UsernameNotFoundException("User not found with id : " + id);
        }

        return UserPrincipal.create(loginInfo);
    }
}
