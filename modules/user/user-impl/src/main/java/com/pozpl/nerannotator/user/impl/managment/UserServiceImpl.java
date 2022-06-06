package com.pozpl.nerannotator.user.impl.managment;

import com.pozpl.neraannotator.user.api.ERole;
import com.pozpl.neraannotator.user.api.IUserService;
import com.pozpl.neraannotator.user.api.UserIntDto;
import com.pozpl.neraannotator.user.api.UserSaveResultDto;
import com.pozpl.neraannotator.user.api.registration.UserRegistrationResultDto;
import com.pozpl.nerannotator.shared.exceptions.NerServiceException;
import com.pozpl.nerannotator.user.impl.dao.model.Role;
import com.pozpl.nerannotator.user.impl.dao.model.User;
import com.pozpl.nerannotator.user.impl.dao.repo.RoleRepository;
import com.pozpl.nerannotator.user.impl.dao.repo.UserRepository;
import io.vavr.control.Try;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    /**
     * Find user record by Id
     *
     * @param id
     * @return
     * @throws NerServiceException
     */
    @Override
    public Optional<UserIntDto> findById(Long id) throws NerServiceException {
        try {
            return userRepository.findById(id).map(this::toDto);
        } catch (Exception e) {
            throw new NerServiceException(e);
        }
    }

    /**
     * Find a user by Username
     *
     * @param username
     * @return
     * @throws NerServiceException
     */
    @Override
    public Optional<UserIntDto> findByUserName(final String username) throws NerServiceException {
        try {
            return userRepository.findByUsername(username).map(this::toDto);
        } catch (Exception e) {
            throw new NerServiceException(e);
        }
    }

    /**
     * List users by page request
     *
     * @param pageable
     * @return
     * @throws NerServiceException
     */
    @Override
    public Page<UserIntDto> list(Pageable pageable) throws NerServiceException {
        try {
            return userRepository.list(pageable)
                    .map(this::toDto);
        } catch (Exception e) {
            throw new NerServiceException(e);
        }
    }

    /**
     * Save or create user
     *
     * @param userToSaveDto
     * @return
     * @throws NerServiceException
     */
    @Override
    public UserSaveResultDto save(UserIntDto userToSaveDto) throws NerServiceException {
        final User user;
        final Set<Role> roles = new HashSet<>();
        if(userToSaveDto.getId() != null){
            final Optional<User> userById = userRepository.findById(userToSaveDto.getId());
            if(userById.isEmpty()){
                throw new NerServiceException("Can not find user by Id " + userToSaveDto.getId());
            }
            user = userById.get();

            if(StringUtils.isNoneBlank(userToSaveDto.getEmail()) && ! StringUtils.equals(userToSaveDto.getEmail(), user.getEmail())){
                final Optional<User> foundByEmail = userRepository.findByEmail(userToSaveDto.getEmail());
                if (foundByEmail.isPresent() && ! foundByEmail.get().equals(user)){
                    return UserSaveResultDto.error("Can not change email.");
                }
            }
            if(StringUtils.isNoneBlank(userToSaveDto.getUsername()) && StringUtils.equals(userToSaveDto.getUsername(), user.getUsername())){
                final  Optional<User> foundByUserName = userRepository.findByUsername(userToSaveDto.getUsername());
                if(foundByUserName.isPresent() && ! foundByUserName.get().equals(user)){
                    return UserSaveResultDto.error("Can not change username.");
                }
            }

        }else {
            if (userRepository.existsByUsername(userToSaveDto.getUsername())) {
                return UserSaveResultDto.error("Can not assign username");
            }

            if (userRepository.existsByEmail(userToSaveDto.getEmail())) {
                return UserSaveResultDto.error("Can not assign email");
            }

            // Create new user's account
            user = new User(userToSaveDto.getUsername(),
                    userToSaveDto.getEmail(),
                    RandomStringUtils.randomAlphanumeric(20));
        }

        if(userToSaveDto.getRoles() != null) {
            for (ERole role : userToSaveDto.getRoles()) {
                final Role userRole = roleRepository.findByName(role)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
            }
        }
        user.setEmail(userToSaveDto.getEmail());
        user.setUsername(userToSaveDto.getUsername());
        user.setFirstName(userToSaveDto.getFirstName());
        user.setLastName(userToSaveDto.getLastName());
        user.setRoles(roles);

        userRepository.save(user);

        return UserSaveResultDto.success(toDto(user));
    }

    /**
     * Delete user
     *
     * @param user
     * @throws NerServiceException
     */
    @Override
    public void delete(UserIntDto user) throws NerServiceException {
        try{
            final Optional<User> userToDeleteOpt = userRepository.findById(user.getId());
            if (userToDeleteOpt.isPresent()){
                userRepository.delete(userToDeleteOpt.get());
            }
        }catch (Exception e){
            throw new NerServiceException(e);
        }
    }

    private UserIntDto toDto(User user) {
        return UserIntDto.of(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream().map(r -> r.getName()).collect(Collectors.toList())
        );
    }
}
