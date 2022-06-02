package com.pozpl.nerannotator.user.impl.managment;

import com.pozpl.neraannotator.user.api.IUserService;
import com.pozpl.neraannotator.user.api.UserIntDto;
import com.pozpl.nerannotator.shared.exceptions.NerServiceException;
import com.pozpl.nerannotator.user.impl.dao.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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
            return userRepository.findByUsername(username).map(u -> UserIntDto.of(
                    u.getId(),
                    u.getUsername(),
                    u.getFirstName(),
                    u.getLastName(),
                    u.getEmail(),
                    u.getPassword(),
                    u.getRoles().stream().map(r -> r.getName()).collect(Collectors.toList())
            ));
        }catch (Exception e){
            throw new NerServiceException(e);
        }
    }
}
