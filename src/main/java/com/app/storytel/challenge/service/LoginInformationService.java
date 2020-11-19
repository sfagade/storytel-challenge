package com.app.storytel.challenge.service;

import com.app.storytel.challenge.model.LoginInformation;
import com.app.storytel.challenge.payload.request.LoginInformationRequest;

public interface LoginInformationService {

    LoginInformation saveNewLoginInformation(LoginInformationRequest loginInformationRequest);
    LoginInformation findLoginInformation(Long loginId);
}
