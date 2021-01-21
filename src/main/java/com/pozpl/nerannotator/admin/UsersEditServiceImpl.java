package com.pozpl.nerannotator.admin;

import com.pozpl.nerannotator.auth.dao.model.User;
import com.pozpl.nerannotator.auth.dao.repo.UserRepository;
import com.pozpl.nerannotator.shared.exceptions.NerServiceException;
import com.pozpl.nerannotator.shared.pagination.PageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
public class UsersEditServiceImpl implements IUsersEditService {

    private final int PER_PAGE = 20;

    private final UserRepository userRepository;

    @Autowired
    public UsersEditServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public PageDto<UserEditDto> list(final Integer page,
                                     final User admin) throws NerServiceException {
        try {
            final Integer adjustedPage = page != null && page >= 0 ? page : 0;
            final Page<User> users = userRepository.list(PageRequest.of(adjustedPage, PER_PAGE, Sort.by(Sort.Direction.ASC, "username")));

            return new PageDto(adjustedPage, new Long(users.getTotalElements()).intValue(), PER_PAGE,
                    users.getContent().stream().map(this::toDto).collect(Collectors.toList()));
        }catch (Exception e){
            throw new NerServiceException(e);
        }
    }

    @Override
    public UserEditDto get(final Long id,
                           final User admin) throws NerServiceException {
        try {
            final Optional<User> userOpt = userRepository.findById(id);

            if (! userOpt.isPresent()){
                throw new NerServiceException("Can not find user by id " + id + " requested by " + admin.getUsername());
            }

            final User user = userOpt.get();

            return UserEditDto.builder()
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .build();
        }catch (Exception e){
            throw new NerServiceException(e);
        }
    }

    @Override
    public UserEditResultDto save(final UserEditDto userEditDto,
                                  final User admin) throws NerServiceException {
        return null;
    }

    @Override
    public boolean remove(Long id, User admin) throws NerServiceException {
        try {
            final Optional<User> userOpt = userRepository.findById(id);

            if (! userOpt.isPresent()){
                throw new NerServiceException("Can not find user by id " + id + " requested for remove by " + admin.getUsername());
            }

            final User user = userOpt.get();

            userRepository.delete(user);
            return true;
        }catch (Exception e){
            throw new NerServiceException(e);
        }
    }


    private UserEditDto toDto(User user) {
        return UserEditDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .id(user.getId())
                .build();
    }
}
