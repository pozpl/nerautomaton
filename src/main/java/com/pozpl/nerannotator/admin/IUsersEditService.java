package com.pozpl.nerannotator.admin;

import com.pozpl.nerannotator.auth.dao.model.User;
import com.pozpl.nerannotator.shared.exceptions.NerServiceException;
import com.pozpl.nerannotator.shared.pagination.PageDto;

public interface IUsersEditService {

    PageDto<UserEditDto> list(Integer page, User admin) throws NerServiceException;

    UserEditDto get(Long id, User admin) throws NerServiceException;

    UserEditResultDto save(UserEditDto userEditDto, User admin) throws NerServiceException;

    boolean remove(Long id, User admin) throws NerServiceException;
}
