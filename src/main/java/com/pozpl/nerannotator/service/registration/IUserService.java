package com.pozpl.nerannotator.service.registration;

import com.pozpl.nerannotator.persistence.model.User;

public interface IUserService {

	User registerNewUserAccount(UserDto accountDto);
}
