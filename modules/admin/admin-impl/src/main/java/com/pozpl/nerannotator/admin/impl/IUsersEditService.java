package com.pozpl.nerannotator.admin.impl;

import com.pozpl.neraannotator.user.api.UserIntDto;
import com.pozpl.nerannotator.shared.exceptions.NerServiceException;
import com.pozpl.nerannotator.shared.pagination.PageDto;

public interface IUsersEditService {

    PageDto<UserEditDto> list(Integer page, UserIntDto admin) throws NerServiceException;

    UserEditDto get(Long id, UserIntDto admin) throws NerServiceException;

    UserEditResultDto save(UserEditDto userEditDto, UserIntDto admin) throws NerServiceException;

    boolean remove(Long id, UserIntDto admin) throws NerServiceException;
}
