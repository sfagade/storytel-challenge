package com.app.storytel.challenge.respository;

import com.app.storytel.challenge.model.LoginInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginInformationRepository extends JpaRepository<LoginInformation, Long> {
    
    LoginInformation findOneByEmailAddress(String emailAddress);
}
