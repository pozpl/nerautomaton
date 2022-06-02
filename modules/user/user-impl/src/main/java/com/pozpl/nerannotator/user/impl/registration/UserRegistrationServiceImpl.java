package com.pozpl.nerannotator.user.impl.registration;

import com.pozpl.neraannotator.user.api.registration.IUserRegistrationService;
import com.pozpl.neraannotator.user.api.registration.SignupRequest;
import com.pozpl.neraannotator.user.api.registration.UserRegistrationResultDto;
import com.pozpl.neraannotator.user.api.ERole;
import com.pozpl.nerannotator.user.impl.dao.model.Role;
import com.pozpl.nerannotator.user.impl.dao.model.User;
import com.pozpl.nerannotator.user.impl.dao.repo.RoleRepository;
import com.pozpl.nerannotator.user.impl.dao.repo.UserRepository;
import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class UserRegistrationServiceImpl implements IUserRegistrationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public UserRegistrationServiceImpl(UserRepository userRepository,
                                       RoleRepository roleRepository,
                                       PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }


    @Override
    public Try<UserRegistrationResultDto> register(final SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return Try.of(() -> UserRegistrationResultDto.error("Error: Can not register account, if you already have an account please use forgot password link!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return Try.of(() -> UserRegistrationResultDto.error("Error: Can not register account, if you already have an account please use forgot password link!"));
        }

        // Create new user's account
        final User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        final Set<Role> roles = new HashSet<>();

        final Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);

        user.setRoles(roles);
        userRepository.save(user);

        return Try.of(() -> UserRegistrationResultDto.success());
    }
}
