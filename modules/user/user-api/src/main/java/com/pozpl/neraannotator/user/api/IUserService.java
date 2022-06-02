package com.pozpl.neraannotator.user.api;

import com.pozpl.nerannotator.shared.exceptions.NerServiceException;

import java.util.Optional;

public interface IUserService {

    /**
     * Find a user by Username
     * @param username
     * @return
     * @throws NerServiceException
     */
    Optional<UserIntDto> findByUserName(String username) throws NerServiceException;

}
