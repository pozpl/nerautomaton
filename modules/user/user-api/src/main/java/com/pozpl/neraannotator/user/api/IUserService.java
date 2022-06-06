package com.pozpl.neraannotator.user.api;

import com.pozpl.nerannotator.shared.exceptions.NerServiceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IUserService {

    /**
     * Find user record by Id
     * @param id
     * @return
     * @throws NerServiceException
     */
    Optional<UserIntDto> findById(Long id) throws NerServiceException;

    /**
     * Find a user by Username
     * @param username
     * @return
     * @throws NerServiceException
     */
    Optional<UserIntDto> findByUserName(String username) throws NerServiceException;

    /**
     * List users by page request
     * @param pageable
     * @return
     * @throws NerServiceException
     */
    Page<UserIntDto> list(Pageable pageable) throws NerServiceException;

    /**
     * Save or create user
     * @param userToSave
     * @return
     * @throws NerServiceException
     */
    UserSaveResultDto save(UserIntDto userToSave) throws NerServiceException;

    /**
     * Delete user
     * @param user
     * @throws NerServiceException
     */
    void delete(UserIntDto user) throws NerServiceException;

}
