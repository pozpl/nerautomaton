package com.pozpl.nerannotator.admin.impl;

import com.pozpl.neraannotator.user.api.IUserService;
import com.pozpl.neraannotator.user.api.UserIntDto;
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

    private final static int PER_PAGE = 20;

    private final IUserService userRepository;

    @Autowired
    public UsersEditServiceImpl(IUserService userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public PageDto<UserEditDto> list(final Integer page,
                                     final UserIntDto admin) throws NerServiceException {
        try {
            final int adjustedPage = page != null && page >= 0 ? page : 0;
            final Page<UserIntDto> users = userRepository.list(PageRequest.of(adjustedPage, PER_PAGE, Sort.by(Sort.Direction.ASC, "username")));

            return new PageDto<>(adjustedPage, Long.valueOf(users.getTotalElements()).intValue(), PER_PAGE,
                    users.getContent().stream().map(this::toDto).collect(Collectors.toList()));
        }catch (Exception e){
            throw new NerServiceException(e);
        }
    }

    @Override
    public UserEditDto get(final Long id,
                           final UserIntDto admin) throws NerServiceException {
        try {
            final Optional<UserIntDto> userOpt = userRepository.findById(id);

            if (userOpt.isEmpty()){
                throw new NerServiceException("Can not find user by id " + id + " requested by " + admin.getUsername());
            }

            final UserIntDto user = userOpt.get();

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
                                  final UserIntDto admin) throws NerServiceException {
        return null;
    }

    @Override
    public boolean remove(Long id, UserIntDto admin) throws NerServiceException {
        try {
            final Optional<UserIntDto> userOpt = userRepository.findById(id);

            if (userOpt.isEmpty()){
                throw new NerServiceException("Can not find user by id " + id + " requested for remove by " + admin.getUsername());
            }

            final UserIntDto user = userOpt.get();

            userRepository.delete(user);
            return true;
        }catch (Exception e){
            throw new NerServiceException(e);
        }
    }


    private UserEditDto toDto(UserIntDto user) {
        return UserEditDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .id(user.getId())
                .build();
    }
}
