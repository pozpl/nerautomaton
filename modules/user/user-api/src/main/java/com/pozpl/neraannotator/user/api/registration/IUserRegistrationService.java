package com.pozpl.neraannotator.user.api.registration;

import com.pozpl.neraannotator.user.api.UserIntDto;
import io.vavr.control.Try;

public interface IUserRegistrationService {

    Try<UserRegistrationResultDto> register(SignupRequest signupRequest);

}
